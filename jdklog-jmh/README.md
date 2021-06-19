VM OPTIONS:

jdklog:

-D"org.jdkstack.jdklog.logging.core.manager"="org.jdkstack.jdklog.logging.core.manager.StudyJuliLogManager"

-D"org.jdkstack.jdklog.logging.core.config.file"="./conf/jdklog.properties"

log4j2:

-D"log4j2.configurationFile"="./conf/log4j2.xml"

-D"Log4jContextSelector"="org.apache.logging.log4j.core.async.AsyncLoggerContextSelector"
