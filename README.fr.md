![](https://dev.lutece.paris.fr/jenkins/buildStatus/icon?job=test-library-unit-testing-deploy)
[![Alerte](https://dev.lutece.paris.fr/sonar/api/project_badges/measure?project=fr.paris.lutece.plugins%3Alibrary-lutece-unit-testing&metric=alert_status)](https://dev.lutece.paris.fr/sonar/dashboard?id=fr.paris.lutece.plugins%3Alibrary-lutece-unit-testing)
[![Line of code](https://dev.lutece.paris.fr/sonar/api/project_badges/measure?project=fr.paris.lutece.plugins%3Alibrary-lutece-unit-testing&metric=ncloc)](https://dev.lutece.paris.fr/sonar/dashboard?id=fr.paris.lutece.plugins%3Alibrary-lutece-unit-testing)
[![Coverage](https://dev.lutece.paris.fr/sonar/api/project_badges/measure?project=fr.paris.lutece.plugins%3Alibrary-lutece-unit-testing&metric=coverage)](https://dev.lutece.paris.fr/sonar/dashboard?id=fr.paris.lutece.plugins%3Alibrary-lutece-unit-testing)

# Library Lutece Unit testing

## Introduction

Cette bibliothéque propose des classes facilitant la mise en oeuvre de tests unitaire.

 **LuteceTestCase** 

Une classe **LuteceTestCase** permet de créer des classes de test avec tous les services Lutece initialisés (AppPropertiesService, AppPathService, accès base de données,...).

```


public MyTestCase extends LuteceTestCase
{
	...
}
                        
```

 **Mock Objects** 

Des classes 'Mock*' issues de spring-test permettent de créer des objets de substitution, **"Mock Object"** , tels que des requêtes ou des sessions HTTP et d'en définir les paramètres afin de simuler des cas de test.

Dans l'exemple ci-dessous, un objet requête HTTP contenant un utilisateur identifé a été créée afin de tester une AdminFeature soumise à authentification.


```


MockHttpServletRequest request = new MockHttpServletRequest( );
Utils.registerAdminUserWithRigth( request, new AdminUser( ), RIGHT_MY_FEATURE);
request.addParameter( PARAM_NANE, param_value );

MyPluginJspBean instance = new MyPluginJspBean( );

instance.init( request, RIGHT_MY_FEATURE );
instance.getMyFeature( request );
                        
```



[Maven documentation and reports](https://dev.lutece.paris.fr/plugins/library-lutece-unit-testing/)



 *generated by [xdoc2md](https://github.com/lutece-platform/tools-maven-xdoc2md-plugin) - do not edit directly.*