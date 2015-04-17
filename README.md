# activiti-form-rest-service
This repository is an sample REST functionality, which we can use this in Alfresco Activiti app forms as dynamic dropdown options.

# Tested software versions
Alfresco : 4.2

Activiti App : 1.1 & 1.2

# Build & Deployment
## Build rest-service 

mvn clean package

java -jar target/rest-service-`VERSION`.jar 

## Build activiti-form-setup

mvn clean package

Copy target/activiti-form-setup-`VERSION`.jar file into `Activiti App Installation Dir`/tomcat/webapps/activiti-app/WEB-INF/lib/

Restart activiti app

# Configuration
## rest-service configurations
rest-service configuration is in src/main/resources/application.properties

You can configure the rest service listening port by below :

server.port=7071

You can do following Alfresco CMIS configurations :

### Alfresco host by :
alfresco.host=localhost

### Alfresco port by :
alfresco.port=8090

### Alfresco admin username & password
alfresco.username=admin

alfresco.password=admin

### Alfresco site name which Insurance policies are defined
alfresco.site.id=insurance-claim

## activiti-form-setup configurations
activiti-form-setup configuration is in `Activiti App Installation Dir`/tomcat/webapps/activiti-app/WEB-INF/classes/META-INF/activiti-app/activiti-app.properties

### You have to configure email property id, which you created in form.
form.email.property.id=intiatorEmail

# Prerequisites in Alfresco
The site, which is mentioned above should be created.

In this sample, We keep the policies in the alfresco as folder structure shown in below

Root folder will be /Sites/insurance-claim/Document Library/

* 'User 1' (email address)
  * 'Policy 1'
    * 'Claim1'
    * 'Claim2'
    * ...
  * 'Policy2'
  * ...
* 'User 2'
* ....

# Setup Activiti App for REST service
Once you run above service, you can configure below REST url in your form to retrieve dynamic drop-down options.
In this sample code, we retrive available policies for current user based on his email address.
To configure the rest url for your drop-down :
- Open the form in 'Form editor'
- Create a textbox for Email address with the id which we configured above (ex: intiatorEmail)
- Create a drop-down
- Select the drop-down & Click 'Edit' option
- Go to 'Options' tab
- Select 'Rest service' as 'Options type'
- Configure below url in 'Rest utl'

	http://`Host`:`Port`/getPolicies?intiatorEmail=${intiatorEmail}
	
	Host & Port you have to replace with your running host & port
	
	intiatorEmail is a form property, which we created earlier
	
- Configure 'Id property'  to id
- Configure 'Label property' to name
- Save the form 
- Open 'Visual Editor' for your process
- Selec Start Event & Click on 'Execution Listeners'
- Add an 'Execution Listener' on 'start' event with com.zaizi.demo.activiti.StartEventListener class
- Save the process
- Redeploy
