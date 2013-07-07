#!/bin/sh
echo ""
echo "Details on RestAPI and current code available at: https://github.com/versly/wsdoc"
echo ""
echo "Read in all the jars in the lib dir..."
export JARS=$(find "build/libs/restApi" -name '*.jar' | xargs echo  | tr ' ' ':')
export classpath=build/classes/main:$JARS

echo ""
echo "Creating the output dir..."
mkdir -p build/docs

echo ""
echo "Run the annotation processor to create the .ser file..."
javac -processor org.versly.rest.wsdoc.AnnotationProcessor -classpath $classpath -sourcepath src/main/java/com/statement/commerce/controller/*.java

echo ""
echo "Generating the doc from the .ser file..."
java  -cp restApi/versly-wsdoc-1.0.jar:$classpath org.versly.rest.wsdoc.RestDocAssembler org.versly.rest.wsdoc.web-service-api.ser  -o build/docs/RestApi.html 

echo ""
echo "Cleaning up the temp .ser file..."
rm org.versly.rest.wsdoc.web-service-api.ser