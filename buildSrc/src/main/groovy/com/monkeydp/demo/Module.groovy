package com.monkeydp.demo

import static com.monkeydp.demo.Symbol.COLON

class Module {

    // 模块名称
    private String name
    // 子模块列表
    private List<Module> submodules = new ArrayList<>()
    // 父模块, null 表示无父模块
    private Module supermodule

    private Module(String name) {
        this.name = name
        this.supermodule = null
    }

    private Module(String name, Module supermodule) {
        this.name = name
        this.supermodule = supermodule
    }

    static Module of(String name) {
        return new Module(name)
    }

    static Module of(String name, Module supermodule) {
        return new Module(name, supermodule)
    }

//    Module appendSubmodules(String... names) {
//        for (int i = 0; i < names.length; i++) {
//            this.submodules.add(Module.of(names[i], this))
//        }
//        return this
//    }

    Module appendSubmodule(Module submodule) {
        this.submodules.add(submodule)
        return this
    }

    // 模块完整名称
    String getFullName() {
        String.join(Symbol.HYPHEN, allNames())
    }

    // 模块路径
    String getPath() {
        def names = this.allNames()
        return COLON + String.join(COLON, names)
    }

    // 项目路径
    String getProjectPath() {
        def builder = new StringBuilder()
        builder.append(this.supermodule.getPath())
            .append(COLON)
            .append(this.getFullName())
        return builder.toString()
    }

    // 递归获取父模块到子模块的名称列表
    private List<String> allNames() {
        def names = new ArrayList<String>()
        names.add(this.getName())

        def supermodule = this.supermodule
        while (supermodule != null) {
            names.add(supermodule.getName())
            supermodule = supermodule.getSupermodule()
        }

        Collections.reverse(names)
        return names
    }

    // getter

    String getName() {
        return name
    }

    Module getSupermodule() {
        return supermodule
    }

    List<Module> getSubmodules() {
        return Collections.unmodifiableList(submodules)
    }
}