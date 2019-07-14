<template>
    <div class="hello">
        <h1>{{ msg }}</h1>
        <div>Got person: {{gotPerson}}</div>
        <br/>
        <div>Updated person: {{updatedPerson}}</div>
    </div>
</template>

<script>
    import ProtoRoot from '../generated/js/proto'

    let Person = ProtoRoot.com.monkeydp.demo.protobuf.protocol.Person
    export default {
        props: {
            msg: String
        },
        data() {
            return {
                gotPerson: null,
                updatedPerson: null,
            }
        },
        methods: {
            getPerson() {
                this.$server.get("/person/get", Person)
                    .then(data => {
                        this.gotPerson = data;
                    })
            },
            updatePersonAndGet(person) {
                // TODO I/O error
                this.$server.put("/person/update-and-get", person)
            },
            getPersonTest() {
                this.getPerson()
            },
            updatePersonAndGetTest() {
                let person = Person.create({name: "iPotato", age: 37})
                let tmp = Person.encode(person).finish()
                this.updatePersonAndGet(tmp)
            }
        },
        created() {
            this.getPersonTest()
            this.updatePersonAndGetTest()
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
