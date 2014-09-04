General Information
===========================
<div style="text-align: justify;">
This is an API client in Java to easily integrate Protectimus two-factor authentication into your project. 

Two-factor authentication from Protectimus is based on one-time passwords (OTP), which are passwords that are valid for only one authentication session.

To integrate Protectimus into your project, you can use these two methods:<br/>
1) Integration using the API, directly or using auxiliary libraries, like this one.<br/>
2) Integration using the IFrame Widget for user authentication. 

Regardless of the method chosen, please start the integration process by reading this page: http://protectimus.com/for-developers. It contains a lot of important information about how the system works and what you have to do to integrate OTP into your system.

Our <a href="http://protectimus.com/materials">materials</a> page can be interesting, too. Among other things, our <a href="http://protectimus.com/images/pdf/Protectimus_API_manual_en.pdf">API documentation</a> can be found there. It can help you better understand exactly what each method does and how to operate all that stuff.

How to Use
===========================
First of all, you need to activate your service plan to enable the API.

There is one entry-point class: ProtectimusApi in the com.protectimus.api.sdk package, through which all operations can be performed. To create an instance of this class, you need to specify the following:
<ul>
<li>apiUrl – the URL to which this client will send requests. If you use our service, the value of this parameter should be  https://api.protectimus.com</li>
<li>username – the name of the administrator from which a request is performed</li>
<li>apikey – the apiKey of the administrator which can be found on the profile page; for service, use this link: https://service.protectimus.com/profile</li>
<li>version – the API version. The current and default version is "v1". </li>
</ul>

All authentications in Protectimus are performed within the scope of a specific resource, so you need to create one. This can be done through the web-interface or via the "addResource" method; you need to specify only the name of the resource and the number of failed authentication attempts, which, if exceeded, results in a user’s being blocked. The value of this parameter should be specified between 3 and 10. If this parameter is not specified, by default, it will be set at 5 attempts.

Then you need to create a user or a token or both, depending on the type of authentication chosen. As usual, it can be done through the web-interface or via the API. Please, look at our <a href="http://protectimus.com/images/pdf/Protectimus_API_manual_en.pdf">API documentation</a> to see the detailed method description. The ID of the created object will be returned, and you can use it (or name/login) to specify the object in further methods calls.

After that, you need to assign them to the resource. The assignment type depends on the authentication type you will perform in the future. The descriptions of the authentication types available and the corresponding assignment types can be found on the above mentioned materials page and in the API documentation.

Now, you can authenticate your user or token or both on the resource you have created. The complete authentication process (with check of filters, the number of failed attempts, and other details) is performed via the API, but if you only need to validate the OTP from a token, you can use the web-interface (the "validate OTP" tab on the token details page).
Please note that if you need to authenticate a Protectimus SMS, Mail or ULTRA token, you have to call the "prepare" method first.  It sends a message with the OTP to the user or generates a challenge for an ULTRA token to authenticate it. 

We have just reviewed the common usage scenario. You may customize it in a way that suits you most. The above mentioned API documentation contains more detailed descriptions of the methods and processes; please review it to better understand the system. 
Also, examples of usage can be found in the com.protectimus.api.sdk.samples package.

If you have any questions, feel free to contact us: support@protectimus.com<br/>
Also, you can learn more about Protectimus on our website: http://protectimus.com

Good Luck! We hope you enjoy it!

<div>
