var store = {
    get(key){
        var val = localStorage.getItem(key)
        return val ? JSON.parse(val) :null
    },
    set(key,val){
        localStorage.setItem(key,JSON.stringify(val))
    }
}