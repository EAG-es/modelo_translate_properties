package inclui.modelo_translate_properties;

import innui.modelos.Modelos;
import innui.modelos.configurations.Initials;
import innui.modelos.configurations.ResourceBundles;
import innui.modelos.errors.Oks;
import innui.modelos.internacionalization.Tr;
import innui.modelo_translate_properties.Folder_with_translations;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import static java.lang.System.exit;
import java.nio.charset.StandardCharsets;
import java.util.ResourceBundle;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;

/**
 *
 * @author emilio
 */
public class Modelo_translate_properties extends Initials {
    // Properties file for translactions
    public static String k_in_route;
    static {
        String paquete_tex = Modelo_translate_properties.class.getPackage().getName();
        paquete_tex = paquete_tex.replace(".", File.separator);
        k_in_route = "in/" + paquete_tex + "/in";
    }
    public static String k_cmdLineSyntax = "cmdLineSyntax"; //NOI18N
    public static int k_line_width = 120;
    public Options options = new Options();
    public CommandLineParser parser = new DefaultParser();
    public Option option_folder_tr;
    public Folder_with_translations file_with_translations = new Folder_with_translations();

    public static void main(String[] args) {
        Oks ok = new Oks();
        try {
            Modelo_translate_properties web3j_api;
            web3j_api = new Modelo_translate_properties();
            web3j_api.run(ok, (Object []) args);
        } catch (Exception e) {
            ok.setTxt(e);
        }
        if (ok.is == false) {
            System.err.println(ok.txt);
            exit(1);
        }
    }
    
    @Override
    public boolean run(Oks ok, Object ... extras_array) throws Exception {
        try {
            if (ok.is == false) { return false; }
//            ResourceBundle in = null;
            // Prepare models: persistent properties (re), internationalization (in),...
            init(ok);
            if (ok.is) { 
                // Get internationalization bundle
//                in = ResourceBundles.getBundle(k_in_route);
                while (true) {
                    String [] args = (String []) extras_array;
                    create_options(ok);
                    if (ok.is == false) { break; }
                    process_options_received(args, ok);
                    if (ok.is == false) { break; }
                    break;
                }
                Oks ok_fin = new Oks();
                terminate(ok_fin);
                if (ok_fin.is == false) {
                    ok.setTxt(ok.getTxt(), ok_fin.getTxt());
                }
            }
            return ok.is;
        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    public boolean init(Oks ok, Object... extra_array) throws Exception {
        // Init models for the clases
        // Models: persistent properties (re), internationalization (in),...
        if (ok.is == false) { return ok.is; }
        _init_from_class(Modelos.class, ok);
        if (ok.is == false) { return ok.is; }
        _init_from_class(this.getClass(), ok);
        return ok.is;
    }
    
    @Override
    public boolean terminate(Oks ok, Object... extra_array) throws Exception {
        // Terminate models for the clases
        // Models: persistent properties (re), internationalization (in),...
        if (ok.is == false) { return ok.is; }
        _terminate_from_class(Modelos.class, ok);
        if (ok.is == false) { return ok.is; }
        _terminate_from_class(this.getClass(), ok);
        return ok.is;
    }
    public boolean write_help(Oks ok, Object ... extra_array) throws Exception {
        if (ok.is == false) { return false; }
        this.write_line(ok.getTxt(), ok);
        HelpFormatter helpFormatter = new HelpFormatter();
        String cmdLineSyntax = this.properties.getProperty(k_cmdLineSyntax);
        helpFormatter.setWidth(k_line_width);
        helpFormatter.printHelp(cmdLineSyntax, options);
        return ok.is;
    }
    /**
     * Creates the CLUI options
     * @param ok
     * @param extra_array
     * @return
     * @throws Exception 
     */
    public boolean create_options(Oks ok, Object ... extra_array) throws Exception {
        ResourceBundle in = null;
        try {
            if (ok.is == false) { return false; }
            in = ResourceBundles.getBundle(k_in_route);
            // F
            option_folder_tr = Option.builder("fj")
                         .longOpt("folder_java")
                         .required()
                         .hasArg()
                         .argName(Tr.in(in, "Path to folder"))
                         .desc(Tr.in(in, "Folder with the Java files with Tr.in"))
                         .build();
            options.addOption(option_folder_tr);
        } catch (Exception e) {
            throw e;
        }
        return ok.is;
    }
    /**
     * Process CLUI options
     * @param args
     * @param ok
     * @param extra_array
     * @return
     * @throws Exception 
     */
    public boolean process_options_received(String [] args, Oks ok, Object ... extra_array) throws Exception {
        ResourceBundle in = null;
        try {
            if (ok.is == false) { return false; }
            in = ResourceBundles.getBundle(k_in_route);
            while (true) {
                CommandLine commandLine = parser.parse(options, args);
                if (commandLine.getArgList().isEmpty() == false) {
                    this.write_line(Tr.in(in, "Unrecognized args"), ok);                
                    if (ok.is == false) { break; }
                    for (String text: commandLine.getArgList()) {
                        this.write_line(text, ok);                
                        if (ok.is == false) { break; }
                    }
                    write_help(ok);
                    if (ok.is == false) { break; }
                } else {
                    for (Option option: commandLine.getOptions()) {
                        process_option(commandLine, option, ok.init());
                        if (ok.is == false) { break; }
                    }
                }
                break;
            }
        } catch (ArrayIndexOutOfBoundsException e) {
            ok.setTxt(e.getLocalizedMessage(), Tr.in(in, "Missing parameters"));
        } catch (Exception e) {
            ok.setTxt(e.getLocalizedMessage());
        }
        if (ok.is == false) {
            this.write_line(ok.getTxt(), ok);
            ok.init();
            write_help(ok);
        }
        return ok.is;
    }    
    /**
     * Process CLUI options
     * @param commandLine
     * @param option
     * @param ok
     * @param extra_array
     * @return
     * @throws Exception 
     */
    public boolean process_option(CommandLine commandLine, Option option, Oks ok, Object ... extra_array) throws Exception {
        ResourceBundle in = null;
        try {
            if (ok.is == false) { return false; }
            in = ResourceBundles.getBundle(k_in_route);
            // B
            if (option.getOpt().equals(option_folder_tr.getOpt())) {
                write_line("--"+option.getLongOpt(), ok);
                if (ok.is == false) { return false; }
                String folder = commandLine.getOptionValue(option_folder_tr);
                String text = file_with_translations.get_properties_text(folder, ok);
                if (ok.is == false) { return false; }
                if (text.isBlank() == false) {
                    String file_name;
                    file_name = folder.replace(File.separator, ".");
                    if (file_name.startsWith(".")) {
                        file_name = file_name.substring(1);
                    }
                    if (file_name.endsWith(".")) {
                        file_name = file_name.substring(0, file_name.length() - 1);
                    }
                    String file_name_in = file_name + ".in.properties";
                    try (FileOutputStream fileOutputStream = new FileOutputStream(file_name_in);
                    OutputStreamWriter outputStreamWriter = new OutputStreamWriter(fileOutputStream, StandardCharsets.UTF_8);
                    BufferedWriter bufferedWriter = new BufferedWriter(outputStreamWriter)) {
                        bufferedWriter.append(text);
                    } catch (Exception e) {
                        ok.setTxt(e.getLocalizedMessage());
                    }
                    text = file_with_translations.get_properties_text_for_translations(ok);
                    if (ok.is == false) { return false; }
                    File file = new File(file_name_in);
                    write_line(Tr.in(in, "File with default texts is written in: ") + file.getCanonicalPath(), ok);
                    if (ok.is == false) { return false; }
                    String file_name_in_to_be_translated = file_name + ".in_to_be_translated.properties";
                    try (FileOutputStream fileOutputStream = new FileOutputStream(file_name_in_to_be_translated);
                    OutputStreamWriter outputStreamWriter = new OutputStreamWriter(fileOutputStream, StandardCharsets.UTF_8);
                    BufferedWriter bufferedWriter = new BufferedWriter(outputStreamWriter)) {
                        bufferedWriter.append(text);
                    } catch (Exception e) {
                        ok.setTxt(e.getLocalizedMessage());
                    }
                    file = new File(file_name_in_to_be_translated);
                    write_line(Tr.in(in, "File with translatable texts is written in: ") + file.getCanonicalPath(), ok);
                    if (ok.is == false) { return false; }
                    text = file_with_translations.get_properties_keys(ok);
                    if (ok.is == false) { return false; }
                    String file_name_keys = file_name + ".in.keys.txt";
                    try (FileOutputStream fileOutputStream = new FileOutputStream(file_name_keys);
                    OutputStreamWriter outputStreamWriter = new OutputStreamWriter(fileOutputStream, StandardCharsets.UTF_8);
                    BufferedWriter bufferedWriter = new BufferedWriter(outputStreamWriter)) {
                        bufferedWriter.append(text);
                    } catch (Exception e) {
                        ok.setTxt(e.getLocalizedMessage());
                    }
                    file = new File(file_name_in_to_be_translated);
                    write_line(Tr.in(in, "File with keys is written in: ") + file.getCanonicalPath(), ok);
                    if (ok.is == false) { return false; }
                    text = file_with_translations.get_properties_values(ok);
                    if (ok.is == false) { return false; }
                    String file_name_values = file_name + ".in.values.txt";
                    try (FileOutputStream fileOutputStream = new FileOutputStream(file_name_values);
                    OutputStreamWriter outputStreamWriter = new OutputStreamWriter(fileOutputStream, StandardCharsets.UTF_8);
                    BufferedWriter bufferedWriter = new BufferedWriter(outputStreamWriter)) {
                        bufferedWriter.append(text);
                    } catch (Exception e) {
                        ok.setTxt(e.getLocalizedMessage());
                    }
                    file = new File(file_name_in_to_be_translated);
                    write_line(Tr.in(in, "File with values is written in: ") + file.getCanonicalPath(), ok);
                    if (ok.is == false) { return false; }
                } else {
                    write_line(Tr.in(in, "None mark text found!"), ok);
                    if (ok.is == false) { return false; }
                }
            }
        } catch (Exception e) {
            ok.setTxt(e.getLocalizedMessage());
        }
        return ok.is;
    }
    
}
