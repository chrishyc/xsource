package freemaker;

import com.google.gson.Gson;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.springframework.util.StringUtils;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

public class CodeGenerator {
    public static void main(String[] args) throws IOException, TemplateException {
        Configuration cfg = new Configuration(Configuration.VERSION_2_3_30);
        cfg.setClassForTemplateLoading(CodeGenerator.class, "/template");
        cfg.setDefaultEncoding("UTF-8");
        
        Template template = cfg.getTemplate("model.ftl");
        String definitionJson = new String(Files.readAllBytes(Paths.get(new File("/Users/chris/xsource/java/src/main/resources/definition/demo.json").toURI())));
        ModelDefinition parsedDefinition = new Gson().fromJson(definitionJson, ModelDefinition.class);
        for (ModelDefinition.Model model : parsedDefinition.getModels()) {
            Map<String, Object> root = new HashMap<>();
            root.put("model", model);
            root.put("rootPackage", parsedDefinition.getRootPackage());
            root.put("genericStr", getGenericStr(model));
            String modelName = StringUtils.capitalize(model.getName()) + ".java";
            File target = new File("/Users/chris/xsource/java/src/main/java/freemaker/"+modelName);
            Writer out = new OutputStreamWriter(new FileOutputStream(target));
            template.process(root, out);
        }
    }
    
    private static String getGenericStr(ModelDefinition.Model model) {
        return model.getProperties().stream().anyMatch(m -> "T".equalsIgnoreCase(m.getType())) ? "<T>" : "";
    }
}
