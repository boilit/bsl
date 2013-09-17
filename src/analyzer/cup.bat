@echo off

@cd /d %~dp0
@set CupHome=%cd%
@cd ..
@set ParentHome=%cd%

@set CupSourceFile=%CupHome%\Parser.cup
@set CupCrePackage=org.boilit.cup
@set CupSymbolClassName=ITokens
@set CupParserClassName=Parser
@set TargetDirectory=%ParentHome%\main\java\org\boilit\bsl\core

@rem set JAVA_HOME=D:\Java\jdk6u38
@rem set PATH=.;%JAVA_HOME%\bin;%PATH%
@rem set CLASSPATH=%JAVA_HOME%\lib\tools.jar;%JAVA_HOME%\lib\dt.jar;%JAVA_HOME%\jre\lib\rt.jar;%CLASSPATH%

@set CupOpts=%CupOpts% -cre %CupCrePackage%
@set CupOpts=%CupOpts% -symbols %CupSymbolClassName%
@set CupOpts=%CupOpts% -parser %CupParserClassName%
@set CupOpts=%CupOpts% -destdir %TargetDirectory%
@set CupOpts=%CupOpts% -interface -noscanner -nopositions

@set CupJar=%CupHome%\cup-0.11plus.jar
@java -Dfile.encoding=UTF-8 -jar %CupJar% %CupOpts% %CupSourceFile%

@pause