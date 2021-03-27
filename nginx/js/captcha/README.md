# Captcha

## reCAPTCHA
Settings
- [https://www.google.com/recaptcha/admin](https://www.google.com/recaptcha/admin)
- Label, reCAPTCHA type, Domains, Owners
- Copy site key
- `https://www.google.com/recaptcha` -> `https://recaptcha.net/recaptcha`

reCAPTCHA v2
- `recaptcha-onload-callback.html`
- `recaptcha-multiple-widgets.html`

## hCaptcha
You can find your site key on your [profile page](https://dashboard.hcaptcha.com/settings).
```
<html>
  <head>
    <title>hCaptcha Demo</title>
    <script src="https://hcaptcha.com/1/api.js" async defer></script>
  </head>
  <body>
    <form action="" method="POST">
      <input type="text" name="email" placeholder="Email" />
      <input type="password" name="password" placeholder="Password" />
      <div class="h-captcha" data-sitekey="your_site_key"></div>
      <br />
      <input type="submit" value="Submit" />
    </form>
  </body>
</html>
```

## References
- [reCAPTCHA v2](https://developers.google.com/recaptcha/docs/display)
- [reCAPTCHA v3](https://developers.google.com/recaptcha/docs/v3)
- [在国内使用Google验证码reCaptcha](https://www.cnblogs.com/loeyo/p/13897509.html)
- [hCaptcha Developer Guide](https://docs.hcaptcha.com/)