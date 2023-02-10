FROM hub.furycloud.io/mercadolibre/java:17-mini

# Base de datos tipo test
#ENV SCOPE="test"
#ENV DB_USER="anueldiaz"
#ENV DB_PASS="FXXzz!678"
#ENV SCHEMA_NAME="javaw20g07"
#ENV DB_HOST="proxysql.master.meliseginf.com:6612"

#Base de datos productiva
ENV SCOPE="test"
ENV DB_USER="anueldiaz"
ENV DB_PASS="FXXzz!678"
ENV SCHEMA_NAME="javag7prod"
ENV DB_HOST="proxysql.master.meliseginf.com:6612"
