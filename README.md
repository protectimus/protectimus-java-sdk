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
There is one entry-point class: ProtectimusApi in com.protectimus.api.sdk package, throught which all operations can be performed. To create an instance of this class you need specify following stuff:
<ul>
<li>apiUrl - URL, to which requests will be sent. If you use our service value of this parameter should be  https://api.protectimus.com</li>
<li>username - name of the administrator, from which request is performed</li>
<li>apikey - apiKey of the administrator, it can be found in profile page, for service the link is: https://service.protectimus.com/profile</li>
<li>version - version of the API. Current version is "v1". </li>
</ul>



If you will have any questions - feel free to contact us: support@protectimus.com<br/>
Also, you can learn more about Protectimus at our web-site: http://protectimus.com

Good Luck! We hope you will enjoy it!

<div>
    
