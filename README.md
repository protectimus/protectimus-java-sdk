General Information
===========================
<div style="text-align: justify;">
This is an API-client on Java to easily integrate Protectimus two-factor authentication into your project. 

Two-factor authentication from Protectimus based on one-time passwords (OTP) - passwords that are valid for only one authentication session.

To integrate Protectimus into your project, you can use these two methods:<br/>
1) Integration using the API, directly or with help of auxilary libraries like this one.<br/>
2) Integration using the IFrame Widget for user authentication. 

Independently of the way you have choosed, please start integration from reading this page: http://protectimus.com/for-developers. It contains a lot of important information about how the system works and what you have to do to integrate OTP into your system.

Our <a href="http://protectimus.com/materials">materials</a> page can be interested too. Among other things our <a href="http://protectimus.com/images/pdf/Protectimus_API_manual_en.pdf">API documentation</a> can be found there. It can helps you better understand what exactly each method do and how to operate with all that stuff.

How to Use
===========================
First of all, you need to activate your tarif plan to enable API.

There is one entry-point class: ProtectimusApi in com.protectimus.api.sdk package, throught which all operations can be performed. To create an instance of this class you need specify following stuff:
<ul>
<li>apiUrl - URL, to which this client will sent requests. If you use our service value of this parameter should be  https://api.protectimus.com</li>
<li>username - name of the administrator, from which request is performed</li>
<li>apikey - apiKey of the administrator, it can be found in profile page, for service the link is: https://service.protectimus.com/profile</li>
<li>version - version of the API. Current and default version is "v1". </li>
</ul>

All authentications in Protectimus are performed in the scope of specific resource, so you need to create one. This can be done throught web-interface or via method "addResource", you need specify only name of the resource and the number of failed authentication attempts, which, if exceeded, results in a user’s being blocked. The value of this parameter  should be specified between 3 and 10. If this parameter is not specified, by default, it will be set at 5 attempts.

Then you need to create a user or a token or both, dependly of which type of authentication you have choosed. As usual, it can be done throught web-interface or via API. Please, look at our <a href="http://protectimus.com/images/pdf/Protectimus_API_manual_en.pdf">API documentation</a> to see detateiled methods description. Id of the created object will be returned, you can use it (or name/login) to specify an object in further methods calls.

After that, you need assign them to the resource. Type of assigning depends on authentication type you will perform in future. Description of available authentication types and corresponding assigning types can be found in mentioned material's page and API documentation.

Now, you can authenticate your user or token or both on the resource you have created. Full authentication process (with check of filters, failed attempts number and other stuff) is performed via API, but if you only need to validate OTP from token - you can use web-interface (tab "validate OTP" of the token's details page).

We have just considered common usage scenario. You may customize it in a way you need. Mentioned API documention contains more detailed description of the methods and process, check it to better understand the system. 

If you will have any questions - feel free to contact us: support@protectimus.com<br/>
Also, you can learn more about Protectimus at our web-site: http://protectimus.com

Good Luck! We hope you will enjoy it!

<div>
    
