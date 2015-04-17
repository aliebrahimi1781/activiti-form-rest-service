# activiti-form-rest-service
This repository is an sample REST functionality, which we can use this in Alfresco Activiti app forms as dynamic dropdown options.

# Tested software versions
Alfresco : 4.2

Activiti App : 1.1 & 1.2

# Build & Run
Build the project by below command :

mvn clean package

And run it by

java -jar target/activiti-form-rest-0.1.0.jar

# Configuration
All configurations are in src/main/resources/application.properties

## Rest service configurations
You can configure the rest service listening port by below :

server.port=7071

## Alfresco CMIS configurations
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

# Deployment in Activiti App
Once you run above service, you can configure below REST url in your form to retrieve dynamic drop-down options.
In this sample code, we retrive available policies for current user based on his email address.
To configure the rest url for your drop-down :
- Select the drop-down in 'Form editor'
- Click 'Edit' option
- Go to 'Options' tab
- Select 'Rest service' as 'Options type'
- Configure below url in 'Rest utl'

	http://`Host`:`Port`/getPolicies?intiatorEmail=${intiatorEmail}
	
	Host & Port you have to replace with your running host & port
	
	intiatorEmail is a workflow variable, you have to set the email address to it in the startup by ExecutionListener
	
- Configure 'Id property'  to id
- Configure 'Label property' to name
- Save the form & redploy
