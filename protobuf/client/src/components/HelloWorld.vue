<template>
    <div class="hello">
        <h1>{{ msg }}</h1>
        <el-row>
            <el-col :span="8">
                &nbsp;
            </el-col>
            <el-col :span="8">
                <el-form v-for="value, key in person">
                    <el-form-item :label="key">
                        <el-input v-model="person[key]"></el-input>
                    </el-form-item>
                </el-form>
            </el-col>
        </el-row>
        <el-button type="primary" @click="handleUpdatePersonAndGet">更新</el-button>
    </div>
</template>

<script>
    import ProtoRoot from '../generated/js/proto'
    import {MediaType} from '../js/Server'

    let Person = ProtoRoot.com.monkeydp.demo.protobuf.protocol.Person

    export default {
        props: {
            msg: String
        },
        data() {
            return {
                person: {},
            }
        },
        methods: {
            getPerson() {
                return this.$server.myserver.get({
                    path: "/person/get",
                    contentType: MediaType.PROTOBUF,
                })
            },
            updatePersonAndGet(person) {
                return this.$server.myserver.put({
                    path: "/person/update-and-get",
                    data: person,
                    contentType: MediaType.PROTOBUF,
                })
            },
            handleUpdatePersonAndGet() {
                this.updatePersonAndGet(this.person)
                    .then(person => {
                        this.person = person
                        this.$message.success("更新成功")
                    })
            }
        },
        created() {
            this.getPerson()
                .then(person => {
                    this.person = person
                })
        }
    }
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
    h3 {
        margin: 40px 0 0;
    }

    ul {
        list-style-type: none;
        padding: 0;
    }

    li {
        display: inline-block;
        margin: 0 10px;
    }

    a {
        color: #42b983;
    }
</style>
