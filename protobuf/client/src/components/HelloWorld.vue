<template>
    <div class="hello">
        <h1>{{ msg }}</h1>
        <div>
            <button @click="getPersonTest">Got person</button>
            {{gotPerson}}
        </div>
        <br/>
        <div>
            <button @click="updatePersonAndGetTest">Updated person</button>
            {{updatedPerson}}
        </div>
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
                return this.$server.get("/person/get")
            },
            updatePersonAndGet(person) {
                return this.$server.put("/person/update-and-get", person)
            },
            getPersonTest() {
                this.getPerson()
                    .then(person => {
                        this.gotPerson = person
                    })
            },
            updatePersonAndGetTest() {
                let person = Person.create({name: "iPotato", age: 37})
                this.updatePersonAndGet(person)
                    .then(data => {
                        this.updatedPerson = data
                    })
            }
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
