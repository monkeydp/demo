<template>
    <div class="hello">
        <h1>{{ msg }}</h1>
        <el-row>
            <el-col :span="8">
                &nbsp;
            </el-col>
            <el-col :span="8">
                <el-form v-for="(value, key) in person" :key="key">
                    <el-form-item :label="key">
                        <el-input v-model="person[key]"></el-input>
                    </el-form-item>
                </el-form>
            </el-col>
        </el-row>
        <el-button type="primary" @click="updatePersonAndGet">更新</el-button>
    </div>
</template>

<script>


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
                this.$api.person.get()
                    .then(person => {
                        this.person = person
                    })
            },
            updatePersonAndGet() {
                return this.$api.person.updateAndGet(this.person)
                    .then(person => {
                        this.person = person
                        this.$message.success("更新成功")
                    })
            }
        },
        created() {
            this.getPerson()
        },
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
