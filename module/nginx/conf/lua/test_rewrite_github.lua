if ngx.req.get_uri_args()["jump"] == "1" then
    return ngx.redirect("https://github.com/T5750?jump=1", 302)
end