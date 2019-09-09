package rts.util;

public class Translit {

    private static final String[] charTable = new String[65536];
    static {
        charTable['À'] = "A";
        charTable['Á'] = "B";
        charTable['Â'] = "V";
        charTable['Ã'] = "G";
        charTable['Ä'] = "D";
        charTable['Å'] = "E";
        charTable['¨'] = "E";
        charTable['Æ'] = "ZH";
        charTable['Ç'] = "Z";
        charTable['È'] = "I";
        charTable['É'] = "I";
        charTable['Ê'] = "K";
        charTable['Ë'] = "L";
        charTable['Ì'] = "M";
        charTable['Í'] = "N";
        charTable['Î'] = "O";
        charTable['Ï'] = "P";
        charTable['Ð'] = "R";
        charTable['Ñ'] = "S";
        charTable['Ò'] = "T";
        charTable['Ó'] = "U";
        charTable['Ô'] = "F";
        charTable['Õ'] = "H";
        charTable['Ö'] = "C";
        charTable['×'] = "CH";
        charTable['Ø'] = "SH";
        charTable['Ù'] = "SH";
        charTable['Ú'] = "";
        charTable['Û'] = "Y";
        charTable['Ü'] = "";
        charTable['Ý'] = "E";
        charTable['Þ'] = "U";
        charTable['ß'] = "YA";

        for (int i = 0; i < charTable.length; i++) {
            char idx = (char) i;
            char lower = new String(new char[] {idx}).toLowerCase().charAt(0);
            if (charTable[i] != null) {
                charTable[lower] = charTable[i].toLowerCase();
            }
        }
    }


    public static String getTranslitText(String text) {
        char charBuffer[] = text.toCharArray();
        StringBuffer sb = new StringBuffer(text.length());
        for (int i = 0; i < charBuffer.length; i++) {
            String replace = charTable[charBuffer[i]];
            if (replace == null) {
                sb.append(charBuffer[i]);
            } else {
                sb.append(replace);
            }
        }
        return sb.toString();
    }
}