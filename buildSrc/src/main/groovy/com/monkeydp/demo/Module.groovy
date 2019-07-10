package com.monkeydp.demo

import static com.monkeydp.demo.Symbol.COLON

class Module {

    // 模块名称
    private String name
    // 子模块列表
    private List<Module> submodules = new ArrayList<>()
    // 父模块, null 表示无父模块
    private Module parent

    private Module(String name) {
        this.name = name
        this.parent = null
    }

    private Module(String name, Module parent) {
        this.name = name
        this.parent = parent
    }

    static Module of(String name) {
        return new Module(name)
    }

    static Module of(String name, Module parent) {
        return new Module(name, parent)
    }

    Module appendSubmodules(String... names) {
        for (int i = 0; i < names.length; i++) {
            this.submodules.add(Module.of(names[i], this))
        }
        return this
    }

    // 模块路径
    String getPath() {
        def names = this.allNames()
        return COLON + String.join(COLON, names)
    }

    // 递归获取父模块到子模块的名称列表
    private List<String> allNames() {
        def names = new ArrayList<String>()
        names.add(this.getName())

        def parent = this.parent
        while (parent != null) {
            names.add(parent.getName())
            parent = parent.getParent()
        }

        Collections.reverse(names)
        return names
    }

    // getter

    String getName() {
        return name
    }

    Module getParent() {
        return parent
    }

    List<Module> getSubmodules() {
        return Collections.unmodifiableList(submodules)
    }
}