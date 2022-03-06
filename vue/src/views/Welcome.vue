<template>
    <div style="margin: 150px auto; text-align: center">
        <h2>欢迎，{{nickname}}</h2>
    </div>
</template>

<script>
    import request from "../util/request";

    export default {
        name: "Welcome",
        data(){
            return{
                nickname: ""
            }
        },
        created() {
            this.load()
        },
        methods: {
            load(){
                request.get("/admin/queryByUsername?username="+sessionStorage.getItem("username")).then(res => {
                    this.nickname = res.data.nickname
                })
            }
        }
    }
</script>

<style scoped>

</style>