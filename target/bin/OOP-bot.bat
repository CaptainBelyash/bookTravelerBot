@REM ----------------------------------------------------------------------------
@REM  Copyright 2001-2006 The Apache Software Foundation.
@REM
@REM  Licensed under the Apache License, Version 2.0 (the "License");
@REM  you may not use this file except in compliance with the License.
@REM  You may obtain a copy of the License at
@REM
@REM       http://www.apache.org/licenses/LICENSE-2.0
@REM
@REM  Unless required by applicable law or agreed to in writing, software
@REM  distributed under the License is distributed on an "AS IS" BASIS,
@REM  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
@REM  See the License for the specific language governing permissions and
@REM  limitations under the License.
@REM ----------------------------------------------------------------------------
@REM
@REM   Copyright (c) 2001-2006 The Apache Software Foundation.  All rights
@REM   reserved.

@echo off

set ERROR_CODE=0

:init
@REM Decide how to startup depending on the version of windows

@REM -- Win98ME
if NOT "%OS%"=="Windows_NT" goto Win9xArg

@REM set local scope for the variables with windows NT shell
if "%OS%"=="Windows_NT" @setlocal

@REM -- 4NT shell
if "%eval[2+2]" == "4" goto 4NTArgs

@REM -- Regular WinNT shell
set CMD_LINE_ARGS=%*
goto WinNTGetScriptDir

@REM The 4NT Shell from jp software
:4NTArgs
set CMD_LINE_ARGS=%$
goto WinNTGetScriptDir

:Win9xArg
@REM Slurp the command line arguments.  This loop allows for an unlimited number
@REM of arguments (up to the command line limit, anyway).
set CMD_LINE_ARGS=
:Win9xApp
if %1a==a goto Win9xGetScriptDir
set CMD_LINE_ARGS=%CMD_LINE_ARGS% %1
shift
goto Win9xApp

:Win9xGetScriptDir
set SAVEDIR=%CD%
%0\
cd %0\..\.. 
set BASEDIR=%CD%
cd %SAVEDIR%
set SAVE_DIR=
goto repoSetup

:WinNTGetScriptDir
for %%i in ("%~dp0..") do set "BASEDIR=%%~fi"

:repoSetup
set REPO=


if "%JAVACMD%"=="" set JAVACMD=java

if "%REPO%"=="" set REPO=%BASEDIR%\repo

set CLASSPATH="%BASEDIR%"\etc;"%REPO%"\org\telegram\telegrambots\4.4.0.1\telegrambots-4.4.0.1.jar;"%REPO%"\org\telegram\telegrambots-meta\4.4.0.1\telegrambots-meta-4.4.0.1.jar;"%REPO%"\com\google\inject\guice\4.2.2\guice-4.2.2.jar;"%REPO%"\javax\inject\javax.inject\1\javax.inject-1.jar;"%REPO%"\aopalliance\aopalliance\1.0\aopalliance-1.0.jar;"%REPO%"\com\google\guava\guava\28.0-jre\guava-28.0-jre.jar;"%REPO%"\com\google\guava\failureaccess\1.0.1\failureaccess-1.0.1.jar;"%REPO%"\com\google\guava\listenablefuture\9999.0-empty-to-avoid-conflict-with-guava\listenablefuture-9999.0-empty-to-avoid-conflict-with-guava.jar;"%REPO%"\org\checkerframework\checker-qual\2.8.1\checker-qual-2.8.1.jar;"%REPO%"\com\google\errorprone\error_prone_annotations\2.3.2\error_prone_annotations-2.3.2.jar;"%REPO%"\com\google\j2objc\j2objc-annotations\1.3\j2objc-annotations-1.3.jar;"%REPO%"\org\codehaus\mojo\animal-sniffer-annotations\1.17\animal-sniffer-annotations-1.17.jar;"%REPO%"\com\fasterxml\jackson\core\jackson-annotations\2.9.9\jackson-annotations-2.9.9.jar;"%REPO%"\com\fasterxml\jackson\jaxrs\jackson-jaxrs-json-provider\2.9.9\jackson-jaxrs-json-provider-2.9.9.jar;"%REPO%"\com\fasterxml\jackson\jaxrs\jackson-jaxrs-base\2.9.9\jackson-jaxrs-base-2.9.9.jar;"%REPO%"\com\fasterxml\jackson\module\jackson-module-jaxb-annotations\2.9.9\jackson-module-jaxb-annotations-2.9.9.jar;"%REPO%"\com\fasterxml\jackson\core\jackson-core\2.9.9\jackson-core-2.9.9.jar;"%REPO%"\com\fasterxml\jackson\core\jackson-databind\2.9.9\jackson-databind-2.9.9.jar;"%REPO%"\org\glassfish\jersey\inject\jersey-hk2\2.29\jersey-hk2-2.29.jar;"%REPO%"\org\glassfish\jersey\core\jersey-common\2.29\jersey-common-2.29.jar;"%REPO%"\org\glassfish\hk2\osgi-resource-locator\1.0.3\osgi-resource-locator-1.0.3.jar;"%REPO%"\com\sun\activation\jakarta.activation\1.2.1\jakarta.activation-1.2.1.jar;"%REPO%"\org\glassfish\hk2\hk2-locator\2.5.0\hk2-locator-2.5.0.jar;"%REPO%"\org\glassfish\hk2\external\aopalliance-repackaged\2.5.0\aopalliance-repackaged-2.5.0.jar;"%REPO%"\org\glassfish\hk2\hk2-api\2.5.0\hk2-api-2.5.0.jar;"%REPO%"\org\glassfish\hk2\hk2-utils\2.5.0\hk2-utils-2.5.0.jar;"%REPO%"\org\javassist\javassist\3.22.0-CR2\javassist-3.22.0-CR2.jar;"%REPO%"\org\glassfish\jersey\media\jersey-media-json-jackson\2.29\jersey-media-json-jackson-2.29.jar;"%REPO%"\org\glassfish\jersey\ext\jersey-entity-filtering\2.29\jersey-entity-filtering-2.29.jar;"%REPO%"\org\glassfish\jersey\containers\jersey-container-grizzly2-http\2.29\jersey-container-grizzly2-http-2.29.jar;"%REPO%"\org\glassfish\hk2\external\jakarta.inject\2.5.0\jakarta.inject-2.5.0.jar;"%REPO%"\org\glassfish\grizzly\grizzly-http-server\2.4.4\grizzly-http-server-2.4.4.jar;"%REPO%"\org\glassfish\grizzly\grizzly-http\2.4.4\grizzly-http-2.4.4.jar;"%REPO%"\org\glassfish\grizzly\grizzly-framework\2.4.4\grizzly-framework-2.4.4.jar;"%REPO%"\jakarta\ws\rs\jakarta.ws.rs-api\2.1.5\jakarta.ws.rs-api-2.1.5.jar;"%REPO%"\org\glassfish\jersey\core\jersey-server\2.29\jersey-server-2.29.jar;"%REPO%"\org\glassfish\jersey\core\jersey-client\2.29\jersey-client-2.29.jar;"%REPO%"\org\glassfish\jersey\media\jersey-media-jaxb\2.29\jersey-media-jaxb-2.29.jar;"%REPO%"\jakarta\annotation\jakarta.annotation-api\1.3.4\jakarta.annotation-api-1.3.4.jar;"%REPO%"\javax\validation\validation-api\2.0.1.Final\validation-api-2.0.1.Final.jar;"%REPO%"\jakarta\xml\bind\jakarta.xml.bind-api\2.3.2\jakarta.xml.bind-api-2.3.2.jar;"%REPO%"\jakarta\activation\jakarta.activation-api\1.2.1\jakarta.activation-api-1.2.1.jar;"%REPO%"\org\json\json\20180813\json-20180813.jar;"%REPO%"\org\apache\httpcomponents\httpclient\4.5.9\httpclient-4.5.9.jar;"%REPO%"\org\apache\httpcomponents\httpcore\4.4.11\httpcore-4.4.11.jar;"%REPO%"\commons-logging\commons-logging\1.2\commons-logging-1.2.jar;"%REPO%"\commons-codec\commons-codec\1.11\commons-codec-1.11.jar;"%REPO%"\org\apache\httpcomponents\httpmime\4.5.9\httpmime-4.5.9.jar;"%REPO%"\commons-io\commons-io\2.6\commons-io-2.6.jar;"%REPO%"\org\apache\logging\log4j\log4j-core\2.12.0\log4j-core-2.12.0.jar;"%REPO%"\org\apache\logging\log4j\log4j-api\2.12.0\log4j-api-2.12.0.jar;"%REPO%"\com\google\api-client\google-api-client\1.23.0\google-api-client-1.23.0.jar;"%REPO%"\com\google\oauth-client\google-oauth-client\1.23.0\google-oauth-client-1.23.0.jar;"%REPO%"\com\google\http-client\google-http-client\1.23.0\google-http-client-1.23.0.jar;"%REPO%"\com\google\code\findbugs\jsr305\1.3.9\jsr305-1.3.9.jar;"%REPO%"\com\google\http-client\google-http-client-jackson2\1.23.0\google-http-client-jackson2-1.23.0.jar;"%REPO%"\com\google\guava\guava-jdk5\17.0\guava-jdk5-17.0.jar;"%REPO%"\com\google\apis\google-api-services-drive\v3-rev105-1.23.0\google-api-services-drive-v3-rev105-1.23.0.jar;"%REPO%"\com\google\oauth-client\google-oauth-client-jetty\1.23.0\google-oauth-client-jetty-1.23.0.jar;"%REPO%"\com\google\oauth-client\google-oauth-client-java6\1.23.0\google-oauth-client-java6-1.23.0.jar;"%REPO%"\org\mortbay\jetty\jetty\6.1.26\jetty-6.1.26.jar;"%REPO%"\org\mortbay\jetty\jetty-util\6.1.26\jetty-util-6.1.26.jar;"%REPO%"\org\mortbay\jetty\servlet-api\2.5-20081211\servlet-api-2.5-20081211.jar;"%REPO%"\org\webjars\npm\whatwg-url\6.5.0\whatwg-url-6.5.0.jar;"%REPO%"\org\webjars\npm\lodash.sortby\4.7.0\lodash.sortby-4.7.0.jar;"%REPO%"\org\webjars\npm\tr46\1.0.1\tr46-1.0.1.jar;"%REPO%"\org\webjars\npm\punycode\2.1.1\punycode-2.1.1.jar;"%REPO%"\org\webjars\npm\webidl-conversions\4.0.2\webidl-conversions-4.0.2.jar;"%REPO%"\io\github\classgraph\classgraph\4.0.0-beta-12\classgraph-4.0.0-beta-12.jar;"%REPO%"\junit\junit\4.13-rc-2\junit-4.13-rc-2.jar;"%REPO%"\org\hamcrest\hamcrest-core\1.3\hamcrest-core-1.3.jar;"%REPO%"\org\junit\jupiter\junit-jupiter\5.6.0-M1\junit-jupiter-5.6.0-M1.jar;"%REPO%"\org\junit\jupiter\junit-jupiter-api\5.6.0-M1\junit-jupiter-api-5.6.0-M1.jar;"%REPO%"\org\apiguardian\apiguardian-api\1.1.0\apiguardian-api-1.1.0.jar;"%REPO%"\org\opentest4j\opentest4j\1.2.0\opentest4j-1.2.0.jar;"%REPO%"\org\junit\platform\junit-platform-commons\1.6.0-M1\junit-platform-commons-1.6.0-M1.jar;"%REPO%"\org\junit\jupiter\junit-jupiter-params\5.6.0-M1\junit-jupiter-params-5.6.0-M1.jar;"%REPO%"\org\junit\jupiter\junit-jupiter-engine\5.6.0-M1\junit-jupiter-engine-5.6.0-M1.jar;"%REPO%"\org\junit\platform\junit-platform-engine\1.6.0-M1\junit-platform-engine-1.6.0-M1.jar;"%REPO%"\org\jetbrains\annotations\18.0.0\annotations-18.0.0.jar;"%REPO%"\OOPproject\BookTravelerBot\1.0-SNAPSHOT\BookTravelerBot-1.0-SNAPSHOT.jar

set ENDORSED_DIR=
if NOT "%ENDORSED_DIR%" == "" set CLASSPATH="%BASEDIR%"\%ENDORSED_DIR%\*;%CLASSPATH%

if NOT "%CLASSPATH_PREFIX%" == "" set CLASSPATH=%CLASSPATH_PREFIX%;%CLASSPATH%

@REM Reaching here means variables are defined and arguments have been captured
:endInit

%JAVACMD% %JAVA_OPTS%  -classpath %CLASSPATH% -Dapp.name="OOP-bot" -Dapp.repo="%REPO%" -Dapp.home="%BASEDIR%" -Dbasedir="%BASEDIR%" com.company.Main %CMD_LINE_ARGS%
if %ERRORLEVEL% NEQ 0 goto error
goto end

:error
if "%OS%"=="Windows_NT" @endlocal
set ERROR_CODE=%ERRORLEVEL%

:end
@REM set local scope for the variables with windows NT shell
if "%OS%"=="Windows_NT" goto endNT

@REM For old DOS remove the set variables from ENV - we assume they were not set
@REM before we started - at least we don't leave any baggage around
set CMD_LINE_ARGS=
goto postExec

:endNT
@REM If error code is set to 1 then the endlocal was done already in :error.
if %ERROR_CODE% EQU 0 @endlocal


:postExec

if "%FORCE_EXIT_ON_ERROR%" == "on" (
  if %ERROR_CODE% NEQ 0 exit %ERROR_CODE%
)

exit /B %ERROR_CODE%
