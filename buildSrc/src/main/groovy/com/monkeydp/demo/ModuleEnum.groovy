package com.monkeydp.demo

/**
 * @author iPotato* @date 2019/7/10
 */
enum ModuleEnum {

    EVENT("event"),
    THRIFT("thrift"),
    THRIFT_SERVER("server", THRIFT),
    THRIFT_CLIENT("client", THRIFT),
    THRIFT_PROTOCOL("protocol", THRIFT),

    private Module module

    ModuleEnum(String moduleName) {
        this.module = Module.of(moduleName)
    }

    ModuleEnum(String moduleName, ModuleEnum supermoduleEnum) {
        Module supermodule = supermoduleEnum.getModule()
        this.module = Module.of(moduleName, supermodule)
        supermodule.appendSubmodule(this.module)
    }

    public Module getModule() {
        return this.module
    }

    public static List<Module> getFirstLayerModules() {
        def modules = new ArrayList()
        Arrays.asList(ModuleEnum.values()).each { moduleEnum ->
            if (isFirstLayer(moduleEnum)) {
                modules.add(moduleEnum.getModule())
            }
        }
        return modules
    }

    private static isFirstLayer(ModuleEnum moduleEnum) {
        return moduleEnum.getModule().getSupermodule() == null;
    }

    public String getProjectPath() {
        return this.getModule().getProjectPath()
    }
}