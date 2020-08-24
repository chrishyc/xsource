package freemaker;

import lombok.Data;

import java.util.List;

@Data
public class ModelDefinition {

    private String groupId;

    private String artifactId;

    private String description;

    private String version;

    private String rootPackage;

    private String modelPackage;

    private String host;

    private String apiVersion;

    private List<Model> models;
    
    private List<Enum> enums;
    
    private List<ApiGroup> apiGroups;

    private SwaggerDefinition swagger;

    private List<Dependency> dependencies;

    @Data
    public static class Dependency {

        private String groupId;

        private String artifactId;

        private String version;
    }
    
    @Data
    public static class Enum {
        private String name;
        private List<Property> properties;
        private List<String> categories;
        
        @Data
        public static class Property {
            private String name;
            private String type;
        }
    }
    
    @Data
    public static class Model {

        private String baseClassName;

        private String name;

        private String apiDocDesc;

        private String description;

        private List<Property> properties;

        private boolean hasPublicProperties = true;

        
        @Data
        public static class Property {

            private String name;

            private String type;

            private String comment;

            private String validation;

            private String apiDocDesc;

        }
    }

    @Data
    public static class Api {

        private String subUri;

        private String method;

        private String name;

        private String summary;

        private String apiDocDesc;

        private String requestBody;

        private List<Parameter> parameters;

        private String response;

        private boolean responseCustom;

    }

    @Data
    public static class ApiGroup {

        private String prefix;

        private String clazz;

        private List<Api> apis;
    }

    @Data
    public static class Parameter {

        private String name;

        private String type;

        private boolean onPath;

        private boolean body;

        private boolean bean;

        private boolean real = true;

    }


    @Data
    public static class SwaggerDefinition {

        private String selectorsBasePackage;

        private ApiInfo apiInfo;

        @Data
        public static class ApiInfo {

            private String title;

            private String description;

            private String version;

        }
    }
}
