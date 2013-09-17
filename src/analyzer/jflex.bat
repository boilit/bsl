@echo off

@cd /d %~dp0
@set JFlexHome=%cd%
@cd ..
@set ParentHome=%cd%

@set JFlexSourceFile=%JFlexHome%\Lexer.jflex
@set TargetDirectory=%ParentHome%\main\java\org\boilit\bsl\core

@rem set JAVA_HOME=D:\Java\jdk7u25
@rem set PATH=.;%JAVA_HOME%\bin;%PATH%
@rem set CLASSPATH=%JAVA_HOME%\lib\tools.jar;%JAVA_HOME%\lib\dt.jar;%JAVA_HOME%\jre\lib\rt.jar;%CLASSPATH%

@set JFlexJar=%JFlexHome%\jflex-1.4.3.jar
@java -Dfile.encoding=UTF-8 -jar %JFlexJar% -d %TargetDirectory% %JFlexSourceFile%

@pause