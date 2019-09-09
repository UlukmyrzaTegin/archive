package rts.service;

import java.io.FileInputStream;
import java.io.Serializable;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Map;

import javax.activation.DataHandler;

import org.apache.commons.io.IOUtils;
import org.apache.cxf.attachment.ByteDataSource;
import org.apache.cxf.jaxws.JaxWsProxyFactoryBean;

import rts.data.DatabaseHelper;
import rts.domain.ApplicationForm;
import rts.domain.Attachment;
import rts.domain.MainDetail;
import rts.domain.MainHeader;

import com.vaadin.data.util.sqlcontainer.connection.JDBCConnectionPool;
import com.vaadin.ui.Table;

@SuppressWarnings("serial")
public class RtsService implements Serializable{
	
	public static void getConsolEstim(Table table, Map<String, Object> map, int limit, int offset, JDBCConnectionPool connectionPool){
		table.removeAllItems();
		String query =""+
        		" SELECT  "+
        		"	mh.id, mh.file_date, mh.show_n, cronom, date_shooting, mh.author, mh.directed_n, search_keyword "+
        		" FROM "+
        		"	public.main_header mh "+
				" where 1=1  ";
			
		Connection conn = null;
		for (Map.Entry<String, Object> entry : map.entrySet()) 
			query += " and "+entry.getKey()+" ? ";
		
		query += " GROUP BY  mh.id, mh.file_date, mh.show_n, cronom, date_shooting, mh.author, mh.directed_n, search_keyword";
		query += " ORDER BY  mh.file_date, mh.id  desc";
		query += " LIMIT ? OFFSET ? ";
		try {
			conn = connectionPool.reserveConnection();
			CallableStatement proc = conn.prepareCall(query);
			int i = 1;
			for (Map.Entry<String, Object> entry : map.entrySet()) {
				if  (entry.getValue().getClass() == Integer.class)
					proc.setInt(i++, (Integer) entry.getValue());
				else if (entry.getValue().getClass() == String.class) 
					proc.setString(i++, (String) entry.getValue());
				else if (entry.getValue().getClass() == java.sql.Date.class) 
					proc.setDate(i++, (java.sql.Date) entry.getValue());
			}
			proc.setInt(i++, limit);
			proc.setInt(i++, offset);
			ResultSet rs = proc.executeQuery();
			if (rs != null) {
				while (rs.next()) {
					table.addItem(new Object[] {
							rs.getInt("id"),
							rs.getDate("file_date"),
							rs.getString("show_n"),
							rs.getString("cronom"),
							rs.getDate("date_shooting"),
							rs.getString("author"),
							rs.getString("directed_n"),
							rs.getString("search_keyword")
							},rs.getInt("id"));
				}
			}
			conn.close();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			connectionPool.releaseConnection(conn);
		}
	}	

	public static Integer getTotalNumberOfConsolEstim(Map<String, Object> map, JDBCConnectionPool connectionPool){
		Integer result = 0;
		String query = "" +
				"SELECT " +
				" Count(mh.id) as cnt "+
				" FROM " +
				" public.main_header mh "+
				"where 1=1 ";
		Connection conn = null;
		for (Map.Entry<String, Object> entry : map.entrySet()) 
			query += " and "+entry.getKey()+" ? ";
		try {
			conn = connectionPool.reserveConnection();

			CallableStatement proc = conn.prepareCall(query);
			int i = 1;
			for (Map.Entry<String, Object> entry : map.entrySet()) {
				query += " and "+entry.getKey()+" ? ";
				if  (entry.getValue().getClass() == Integer.class)
					proc.setInt(i++, (Integer) entry.getValue());
				else if (entry.getValue().getClass() == String.class) 
					proc.setString(i++, (String) entry.getValue());
				else if (entry.getValue().getClass() == java.sql.Date.class) 
					proc.setDate(i++, (java.sql.Date) entry.getValue());
			}
			ResultSet rs = proc.executeQuery();
			if (rs != null && rs.next()) {
				result = rs.getInt("cnt");
				}
			conn.close();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			connectionPool.releaseConnection(conn);
		}
		return result;
	}	

	public static ApplicationForm getApplicationFormById(Integer id, DatabaseHelper dbHelper) {
		ApplicationForm appForm = new ApplicationForm();
		ArrayList<Attachment> attachments = new ArrayList<Attachment>();
		ArrayList<MainDetail> md = new ArrayList<MainDetail>();
		MainHeader mh=new MainHeader();
        Connection conn = null;
        CallableStatement proc = null;
        ResultSet rs = null;
        try {
            conn = dbHelper.getConnectionPool().reserveConnection();
            proc = conn.prepareCall("SELECT * FROM public.main_header WHERE id = "+id+";");
            rs = proc.executeQuery();
            if (rs != null) {
                rs.next();
               appForm.setId(id);
               appForm.setAplDate(rs.getDate("file_date"));
               mh.setShow_n(rs.getString("show_n"));
               mh.setCronom(rs.getString("cronom"));
               mh.setDate_shooting(rs.getDate("date_shooting"));
               mh.setAuthor(rs.getString("author"));
               mh.setDirected_n(rs.getString("directed_n"));
               mh.setSearch_keyword(rs.getString("search_keyword"));
               appForm.setMainHeader(mh);
            }
            proc = conn.prepareCall("SELECT * FROM public.main_detail WHERE main_header = "+id+";");
            rs = proc.executeQuery();
            if (rs != null) {
            	while (rs.next()) {
            		Attachment attFile = new Attachment();
            		attFile.setFileName(rs.getString("file_name"));
            		attFile.setMIMEType(rs.getString("mime_type"));
            		attFile.setFileStorageId(rs.getLong("fs"));
            		attachments.add(attFile);
            		MainDetail mainDetail = new MainDetail();
            		mainDetail.setFile_name(rs.getString("file_name"));
            		mainDetail.setMime_type(rs.getString("mime_type"));
            		mainDetail.setFs(rs.getLong("fs"));
            		md.add(mainDetail);
            	}
            }
            appForm.setMainDetail(md);
            appForm.setAttachments(attachments);
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
        	dbHelper.getConnectionPool().releaseConnection(conn);
        }		
		
		return appForm;
	}
	@SuppressWarnings("resource")
	public static String saveApplicationForm(ApplicationForm appForm, JDBCConnectionPool connectionPool){
		//
	    MainHeader mh = appForm.getMainHeader();
		if (appForm.getAplDate() == null) 
			return "Не верно указана дата документа";
		if (mh.getDirected_n() == null) 
			return "Поле Режиссер пустое";
		if (mh.getShow_n() == null) 
			return "Поле Название передачи пустое";
		
		
        Connection conn = null;
        Integer tmp_id = null;
        try {
            conn = connectionPool.reserveConnection();
            conn.setAutoCommit(false);
            PreparedStatement statement = null;
            ResultSet rs = null;
    		if (appForm.getId() == null || appForm.getId() == 0){
        		// card_doc
	            String generatedColumns[] = {"id"};
	            statement = conn.prepareStatement("INSERT INTO public.main_header( file_date, show_n, cronom, date_shooting, author, directed_n, search_keyword ) VALUES ( ?, ?, ?, ?, ?, ?, ?);", generatedColumns);
	            statement.setDate(1, new java.sql.Date(appForm.getAplDate().getTime()) );	 
	            statement.setString(2, appForm.getMainHeader().getShow_n());
	            statement.setString(3, appForm.getMainHeader().getCronom());
	            statement.setDate(4,new java.sql.Date (appForm.getMainHeader().getDate_shooting().getTime()));
	            statement.setString(5, appForm.getMainHeader().getAuthor());
	            statement.setString(6, appForm.getMainHeader().getDirected_n());
	            statement.setString(7, appForm.getMainHeader().getSearch_keyword());
	            statement.executeUpdate(); 
	           rs = statement.getGeneratedKeys();
	            if(rs.next())
	            	tmp_id = rs.getInt(1);
    		}
    		else{
    			tmp_id = appForm.getId();
        		// card_doc
	            statement = conn.prepareStatement("UPDATE main_header set  file_date=?,  show_n =?, cronom=?, date_shooting=?, author=?, directed_n=?, search_keyword=? WHERE id = ?;");
	            statement.setDate(1, new java.sql.Date(appForm.getAplDate().getTime()) );
	            statement.setString(2, appForm.getMainHeader().getShow_n());
	            statement.setString(3, appForm.getMainHeader().getCronom());
	            statement.setDate(4, new java.sql.Date(appForm.getMainHeader().getDate_shooting().getTime()));
	            statement.setString(5, appForm.getMainHeader().getAuthor());
	            statement.setString(6, appForm.getMainHeader().getDirected_n());
	            statement.setString(7, appForm.getMainHeader().getSearch_keyword());
	            statement.setInt(8, tmp_id);
	            statement.executeUpdate(); 
	            statement.clearParameters();
	             
    		}
         
            
            JaxWsProxyFactoryBean factory = new JaxWsProxyFactoryBean();
    		
   		    factory.setServiceClass(DocumentService.class);
    		factory.setAddress(JaxWsProxyFactoryBeanAddress.getAddress());
    		
    		DocumentService service = (DocumentService) factory.create();
    		
    		for (Attachment attachment : appForm.getAttachments()) {
    			if (attachment.getFileStorageId()!=null)
    				continue;
    			IdentifyResponse response = service.create();
                
        		if(response.getCode() == 0){
        			attachment.setFileStorageId(response.getId().longValue());
        			UnitResponse unit = service.loadData(response.getId(), attachment.getMIMEType(), attachment.getFileName(), 
        					new DataHandler(new ByteDataSource(IOUtils.toByteArray(new FileInputStream(attachment.getFile())))), false);
        			if(unit.getCode() != 0) return "ERROR";
    	            statement.clearParameters();
    	            statement = conn.prepareStatement("INSERT INTO public.main_detail(fs, main_header, file_name, mime_type) VALUES (?, ?, ?, ?);");
    	            statement.setLong(1, response.getId().longValue() );	 
    	            statement.setInt(2, tmp_id);
    	            statement.setString(3, attachment.getFileName());
    	            statement.setString(4, attachment.getMIMEType());
    	            statement.executeUpdate(); 
        		}
			}
    		
    		statement.close();
    		
    		
            conn.commit();
            appForm.setId(tmp_id);
            return "";
        } catch (Exception e) {
            e.printStackTrace();
            return "SQL ERROR";
        } finally {
            connectionPool.releaseConnection(conn);
        }		
	}	
}
