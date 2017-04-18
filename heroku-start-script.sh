#point to the correct configuration and webapp
CATALINA_BASE=`pwd`/target/apache-tomcat-8.5.12
export CATALINA_BASE

#copy over the Heroku config files (we already did this with maven)
#cp ./server-heroku.xml ./target/apache-tomcat-8.5.13/conf/server.xml
#cp ./persistence-heroku.xml ./target/apache-tomcat-8.5.13/webapps/ROOT/WEB-INF/classes/META-INF/persistence.xml

#make tomcat scripts executable
chmod a+x ./target/apache-tomcat-*/bin/*.sh

#heroku randomly generates a port to which app must bind, we need to access it via this $PORT variable
#if you use persistence you will also require database connection url, which you must put into a environment var on heroku and reference here
JAVA_OPTS="$JAVA_OPTS -Dhttp.port=$PORT -Dhibernate.connection.url=$DATABASE_URL"
export JAVA_OPTS

#In reality you don't want to leave anything in the webapps dir (certainly not examples apps), so clean it
#clean ALL. WARNING - our app is already in there because of our maven war plugin, so running this will remove also and kill your deployment
#if you use this method, then set the war plugin outputDirectory somewhere else and ruin a cp command to copy the war to webapps after cleanup
#rm -rf $CATALINA_BASE/webapps/* 
#clean example apps
#rm -rf $CATALINA_BASE/webapps/examples
#clean root
#rm -rf $CATALINA_BASE/webapps/ROOT
#clean the deployment manager (IF you don't want a manual server management UI, which you probably don't because Tomcat manager is really barebones and not useful at all)
#rm -rf $CATALINA_BASE/webapps/manager
#rm -rf $CATALINA_BASE/webapps/host-manager
#clean docs unless you for some reason want tomcat documentation be a part of your server
#rm -rf $CATALINA_BASE/webapps/docs

#finally run tomcat. Our app is already in the directory
$CATALINA_BASE/bin/catalina.sh run