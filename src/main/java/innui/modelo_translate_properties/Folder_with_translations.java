package innui.modelo_translate_properties;

import innui.Bases;
import innui.modelos.configurations.ResourceBundles;
import innui.modelos.errors.Oks;
import innui.modelos.internacionalization.Tr;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.Map;
import java.util.Map.Entry;
import java.util.ResourceBundle;
import java.util.TreeMap;

/**
 *
 * @author emilio
 */
public class Folder_with_translations extends Bases {
    // Properties file for translactions
    public static String k_in_route;
    static {
        String paquete_tex = Folder_with_translations.class.getPackage().getName();
        paquete_tex = paquete_tex.replace(".", File.separator);
        k_in_route = "in/" + paquete_tex + "/in";
    }
    public static String k_character_disguiser = "_ _";
    public static String k_character_scape_disguiser = "\\\\";
    public static String k_mark = "Tr"+".in";
    Map<String, String> properties_mapa = new TreeMap<>();
    
    /**
     * Search for literals after k_mark
     * @param path
     * @param properties_map
     * @param ok
     * @param extras_array
     * @return
     * @throws Exception 
     */
    public boolean _search_mark_text_literals_in_path(String path
      , Map<String, String> properties_map
      , Oks ok, Object... extras_array) throws Exception {
        if (ok.is == false) { return false; }
        ResourceBundle in;
        in = ResourceBundles.getBundle(k_in_route);
        try {
            File file;
            File [] files_array;
            file = new File(path);
            if (file.isDirectory()) {
                files_array = file.listFiles(filter -> {
                    if (filter.getName().toLowerCase().endsWith("java")) {
                        return true;
                    } else {
                        return false;
                    }
                });
                for (File nodo_file : files_array) {
                    _search_after_mark_literal_text_in_file(nodo_file, "Tr"+".in"
                      , properties_map, ok, extras_array);
                    if (ok.is == false) { return false; }
                }
            } else {
                ok.setTxt(Tr.in(in, "Not a directory: ") + path);
            }
        } catch (Exception e) {
            ok.setTxt(e);
        }
        return ok.is;
    }
    /**
     * 
     * @param file
     * @param mark
     * @param properties_map
     * @param ok
     * @param extras_array
     * @return
     * @throws Exception 
     */
    public boolean _search_after_mark_literal_text_in_file(File file
      , String mark, Map<String, String> properties_map
      , Oks ok, Object... extras_array) throws Exception {
        if (ok.is == false) { return false; }
        ResourceBundle in;
        in = ResourceBundles.getBundle(k_in_route);
        try {
            if (file.exists() && file.canRead()) {
                int tam = 100;
                if (tam < mark.length()) {
                    tam = mark.length() + 100;
                }
                char [] chars_array = new char[tam];
                int num;
                int pos = 0;
                int found;
                int found_line_comment;
                int found_end_line_comment;
                boolean is_line_comment = false;
                int found_begin_comment;
                int found_end_comment;
                boolean is_begin_comment = false;
                String texto;
                String literal;
                StringBuilder resto = new StringBuilder();
                try (FileInputStream fileInputStream = new FileInputStream(file)) {
                    InputStreamReader InputStreamReader = new InputStreamReader(fileInputStream, "UTF-8");
                    BufferedReader bufferedReader = new BufferedReader(InputStreamReader);
                    while (true) {
                        num = bufferedReader.read(chars_array, pos, chars_array.length - pos);
                        if (num == -1) {
                            if (pos > 0) {
                                num = 0;
                            } else {
                                break;
                            }
                        }
                        texto = String.valueOf(chars_array, 0, num + pos);
                        found = texto.indexOf(mark);
                        if (is_line_comment == false) {
                            found_line_comment = texto.indexOf("//");
                            if (found_line_comment > 0 
                             && found_line_comment < found) {
                                found_end_line_comment = texto.indexOf("\n", found_line_comment);
                                if (found_end_line_comment > 0) { 
                                    if (found_end_line_comment > found) {
                                        found = -1; // it is inside a comment
                                    }
                                } else {
                                    is_line_comment = true;
                                }
                            }
                        } else {
                            found_end_line_comment = texto.indexOf("\n");
                            if (found_end_line_comment > 0) { 
                                if (found_end_line_comment > found) {
                                    found = -1; // it is inside a comment
                                }
                                is_line_comment = false;
                            } else {
                                found = -1; // it is inside a comment
                            }
                        }
                        if (is_begin_comment == false) {
                            found_begin_comment = texto.indexOf("/*");
                            if (found_begin_comment > 0 
                             && found_begin_comment < found) {
                                found_end_comment = texto.indexOf("*/", found_begin_comment);
                                if (found_end_comment > 0) {
                                    if(found_end_comment > found) {
                                        found = -1; // it is inside a comment
                                    }
                                } else {
                                    is_begin_comment = true;
                                }
                            }
                        } else {
                            found_end_comment = texto.indexOf("*/");
                            if (found_end_comment > 0) {
                                if(found_end_comment > found) {
                                    found = -1; // it is inside a comment
                                }
                                is_begin_comment = false;
                            } else {
                                found = -1; // it is inside a comment
                            }
                        }
                        if (found < 0) {
                            int resto_tam = texto.length() - mark.length() + 1;
                            if (resto_tam > 0) {
                                texto = texto.substring(texto.length() - mark.length() + 1);
                                pos = texto.length();
                                int i = 0;
                                while (true) {
                                    if (i >= pos) {
                                        break;
                                    }
                                    chars_array[i] = texto.charAt(i);
                                    i = i + 1;
                                }
                            } else {
                                pos = 0;
                            }
                        } else {
                            literal = _search_literal_text(bufferedReader
                              , texto.substring(found + mark.length())
                              , resto, ok);
                            if (ok.is == false) { return false; }
                            pos = 0;
                            tam = resto.length();
                            while (true) {
                                if (pos >= tam) {
                                    break;
                                }
                                chars_array[pos] = resto.charAt(pos);
                                pos = pos + 1;
                            }
                            properties_map.put(literal, literal);
                        }
                    }
                }
            } else {
                ok.setTxt(Tr.in(in, "Cannot read file: ") + file.getCanonicalPath());
            }
        } catch (Exception e) {
            ok.setTxt(e);
        }
        return ok.is;
    }
    /**
     * Look for a literal enclosed in double quotes with no escaping in front (\)
     * @param bufferedReader
     * @param init
     * @param rest
     * @param ok
     * @param extras_array
     * @return
     * @throws Exception 
     */
    public String _search_literal_text(BufferedReader bufferedReader, String init
      , StringBuilder rest, Oks ok, Object... extras_array) throws Exception {
        if (ok.is == false) { return null; }
        String retorno = null;
        try {
            boolean is_scape = false;
            boolean is_init = false;
            boolean is_exit = false;
            int num;
            int i;
            int tam;
            char [] chars_array = init.toCharArray();
            char [] chars_leidos_array = new char[100];
            String texto;
            retorno = "";
            while (true) {
                i = 0;
                for (char letra: chars_array) {
                    if (letra == '\\') {
                        if (is_scape) {
                            is_scape = false;
                        } else {
                            is_scape = true;                            
                        }
                    } else {
                        is_scape = false;
                    }
                    if (is_scape == false 
                     && letra == '"') {
                        if (is_init == false) {
                            is_init = true;
                        } else {
                            is_exit = true;
                            break;
                        }
                    } else if (is_init) {
                        retorno = retorno + letra;
                    }
                    i = i + 1;
                }
                if (is_exit) {
                    break;
                }
                num = bufferedReader.read(chars_leidos_array);
                if (num == -1) {
                    break;
                }
                texto = String.valueOf(chars_leidos_array);
                chars_array = texto.toCharArray();
            }
            if (is_exit) {
                rest = rest.delete(0, rest.length());
                tam = chars_array.length;
                while (true) {
                    if (i >= tam) {
                        break;
                    }
                    rest.append(chars_array[i]);
                    i = i + 1;
                }
            }
        } catch (Exception e) {
            ok.setTxt(e);
        }
        return retorno;
    }
    
    /**
     *
     * @param path
     * @param ok
     * @param extras_array
     * @return
     * @throws Exception
     */
    public String get_properties_text(String path, Oks ok, Object... extras_array) throws Exception {
        if (ok.is == false) { return null; }
        String retorno = null;
//        ResourceBundle in;
//        in = ResourceBundles.getBundle(k_in_route);
        try {
            _search_mark_text_literals_in_path(path
              , properties_mapa, ok, extras_array);
            if (ok.is == false) { return null; }
            String properties_tex = "";
            String clave;
            for (Entry<String, String> entry: properties_mapa.entrySet()) {
                clave = entry.getKey();
                clave = clave.replace(" ", "\\ ");
                clave = clave.replace(":", "\\:");
                clave = clave.replace("=", "\\=");
                properties_tex = properties_tex
                  + clave
                  + "="
                  + entry.getValue()
                  + "\n";
            }
            retorno = properties_tex;
        } catch (Exception e) {
            ok.setTxt(e);
        }
        return retorno;
    }
    /**
     * Disguises property keys to not be translated (call after get_properties_text)
     * @param ok
     * @param extras_array
     * @return
     * @throws Exception
     */
    public String get_properties_text_for_translations(Oks ok, Object... extras_array) throws Exception {
        if (ok.is == false) { return null; }
        String retorno = null;
//        ResourceBundle in;
//        in = ResourceBundles.getBundle(k_in_route);
        try {
            String properties_tex = "";
            String key;
            for (Entry<String, String> entry: properties_mapa.entrySet()) {
                key = entry.getKey();
                key = key.replace(" ", "\\ ");
                key = key.replace(":", "\\:");
                key = key.replace("=", "\\=");
                properties_tex = properties_tex
                  + _disguise(key, entry.getValue(), ok);
            }
            retorno = properties_tex;
        } catch (Exception e) {
            ok.setTxt(e);
        }
        return retorno;
    }
    /**
     * Disguise a test to not look to belong to any language
     * @param key
     * @param value
     * @param ok
     * @param extras_array
     * @return
     * @throws Exception 
     */
    public String _disguise(String key, String value,Oks ok, Object... extras_array) throws Exception {
        if (ok.is == false) { return null; }
        String retorno = null;
//        ResourceBundle in;
//        in = ResourceBundles.getBundle(k_in_route);
        try {
            String text = "";
            for (char c: key.toCharArray()) {
                text = text + c;
                if (c == '\\') {
                    text = text + '\\';
                }
                text = text + k_character_disguiser;
            }
            if (value.endsWith(" ")) {
                value = value + k_character_disguiser;
            }
            retorno = text
                  + "="
                  + value
                  + "\n";
        } catch (Exception e) {
            ok.setTxt(e);
        }
        return retorno;
    }
    /**
     * Gets the properties keys (call after get_properties_text)
     * @param ok
     * @param extras_array
     * @return
     * @throws Exception
     */
    public String get_properties_keys(Oks ok, Object... extras_array) throws Exception {
        if (ok.is == false) { return null; }
        String retorno = null;
//        ResourceBundle in;
//        in = ResourceBundles.getBundle(k_in_route);
        try {
            String properties_tex = "";
            String key;
            for (Entry<String, String> entry: properties_mapa.entrySet()) {
                key = entry.getKey();
                key = key.replace(" ", "\\ ");
                key = key.replace(":", "\\:");
                key = key.replace("=", "\\=");
                properties_tex = properties_tex
                  + key + "\n";
            }
            retorno = properties_tex;
        } catch (Exception e) {
            ok.setTxt(e);
        }
        return retorno;
    }
    /**
     * Gets the properties values (call after get_properties_text)
     * @param ok
     * @param extras_array
     * @return
     * @throws Exception
     */
    public String get_properties_values(Oks ok, Object... extras_array) throws Exception {
        if (ok.is == false) { return null; }
        String retorno = null;
//        ResourceBundle in;
//        in = ResourceBundles.getBundle(k_in_route);
        try {
            String properties_tex = "";
            String value;
            for (Entry<String, String> entry: properties_mapa.entrySet()) {
                value = entry.getValue();
                if (value.endsWith(" ")) {
                    value = value + k_character_disguiser;
                }
                properties_tex = properties_tex
                  + value + "\n";
            }
            retorno = properties_tex;
        } catch (Exception e) {
            ok.setTxt(e);
        }
        return retorno;
    }
    
}
