var version = '1.1'
var localStorageKey = 'data' + version
var data = {
    ws:null,
    state: false,
    wsUri: null,
    uris:{
        action:false,
        list:[]
    },
    api:{
        req:"",
        res:'',
        title:''
    },
    apiList:[],
    formatObj:{
        open:false,
        defSendFormate: `function(req){
            try{
                return JSON.stringify(JSON.parse(req),null,4)
            }catch(e){
                alert('非标准json格式')
                return req
            }
        }`,
        defReceiveFormate: `function(res){
            if(typeof  res.data === 'string'){
                try{
                    return JSON.stringify(JSON.parse(res.data),null,4)
                }catch(e){
                    return res.data
                }
            }else{
                return '非字符数据'
            }
        }`,        
        sendFormate: `function(req){
            try{
                return JSON.stringify(JSON.parse(req),null,4)
            }catch(e){
                alert('非标准json格式')
                return req
            }
        }`,
        receiveFormate: `function(res){
            if(typeof  res.data === 'string'){
                try{
                    return JSON.stringify(JSON.parse(res.data),null,4)
                }catch(e){
                    return res.data
                }
            }else{
                return '非字符数据'
            }
        }`
    }
}

var localData = store.get(localStorageKey)
if(localData){
    data.wsUri = localData.wsUri
    data.apiList = localData.apiList
    if(localData.uris){
        data.uris = localData.uris
        data.uris.action = false
    }
    if( localData.formatObj){
        data.formatObj = localData.formatObj
        data.formatObj.open = false
    }
}

var vue = new Vue({
    el:'#app',
    data:data,
    methods:{
        conn(){
            if(this.wsUri == null || this.wsUri.length == 0) {
                return;
            }

            var ws = new WebSocket(this.wsUri)
            ws.onopen = function(){
                console.log("连接成功...")
                for (const i in vue.uris.list) {
                    if(vue.wsUri == vue.uris.list[i]){
                        vue.uris.list.splice(i,1)
                        break
                    }
                }
                vue.uris.list.unshift(vue.wsUri)
                if(vue.uris.list.length >= 25){
                    vue.uris.list.pop()
                }
                vue.state = true
                vue.write()
            }
            ws.onclose = function(){
                vue.state = false
            }
            ws.onmessage = function(msg){
                var txt = eval('msg = ('+ vue.formatObj.receiveFormate +')(msg)')
                vue.api.res = "\n\r==>" + new Date().toLocaleString() + "\n\r" + txt + vue.api.res
            }
            this.ws = ws;
        },
        disConn(){
            if(this.ws) {
                this.ws.close();
            }
        },
        send(){
            if(!this.ws || this.ws.readyState == this.ws.CLOSED){
                alert("未连接到服务器")
            }else{
                this.ws.send(this.api.req)
            }
        },
        save(){
            for (const i in this.apiList) {
                var item = this.apiList[i]
                if(item.title == this.api.title){
                    this.apiList.splice(i,1)
                    break
                }
            }
            this.apiList.unshift(JSON.parse(JSON.stringify(this.api)))
            this.write()
        },
        reSelect(item){
            this.api = JSON.parse(JSON.stringify(item))
        },
        del(item){
            if(!confirm('真的要删除吗?')) return

            for (const i in this.apiList) {
                if(item.title == this.apiList[i].title){
                    this.apiList.splice(i,1)
                    break
                }
            }
            this.write()
        },
        write(){
            localStorage.setItem(localStorageKey,JSON.stringify({
                wsUri:vue.wsUri,
                apiList:vue.apiList,
                formatObj:vue.formatObj,
                uris:vue.uris
            }))
        },
        connHistroy(){
            this.uris.action = !this.uris.action
        },
        selectOne(uri){
            this.wsUri = uri
            this.uris.action = false
        },
        clean(){
            this.api.res = ''
        },
        format(){
            eval('this.api.req = ('+ this.formatObj.sendFormate +')(this.api.req)')
        },
        af(){
            if(this.formatObj.open){
                this.write()
            }
            this.formatObj.open = !this.formatObj.open
        },
        resetSendFormat(){
            if(confirm('真的要恢复吗?')){
                this.formatObj.sendFormate =  this.formatObj.defSendFormate
            }
        },
        resetReceiveFormate(){
            if(confirm('真的要恢复吗?')){
                this.formatObj.receiveFormate = this.formatObj.defReceiveFormate
            }
        },
        removeHistroy(index){
            this.uris.list.splice(index,1)
            this.write()
        },
        reset(){
            if(confirm('真的初始化吗?')){
                if(confirm('就是你当初见到我的样子!')){
                    localStorage.setItem(localStorageKey,null)
                    location.reload();
                }
            }
        }
    }
})