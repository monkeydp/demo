package com.monkeydp.demo

/**
 * @author iPotato* @date 2019/7/10
 */
enum ModuleEnum {

    EVENT(Module.of("event")),
    THRIFT(
        Module.of("thrift")
            .appendSubmodules("server", "client", "protocol")
    ),

    private Module module

    ModuleEnum(Module module) {
        this.module = module
    }

    public Module getModule() {
        return this.module
    }

    public static List<Module> getModules() {
        def modules = new ArrayList()
        Arrays.asList(ModuleEnum.values()).each { moduleEnum ->
            modules.add(moduleEnum.getModule())
        }
        return modules
    }
}