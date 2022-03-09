FROM ubuntu:latest

RUN apt-get update && apt-get install openssh-server sudo vim -y
RUN apt-get install -y apache2

# ssh configuration file : /etc/ssh/ssh_config
COPY config/ssh_config /etc/ssh/sshd_config
# create or change user
RUN useradd -m -s /bin/bash test
# username:password
RUN  echo 'test:1234' | chpasswd
RUN  echo 'root:1234' | chpasswd

RUN service ssh start

COPY public/ /var/www/html
COPY config/apache.conf /etc/apache2/apache2.conf

RUN service apache2 start

CMD ["/usr/sbin/sshd","-D"]
CMD ["/usr/sbin/apache2ctl", "-D", "FOREGROUND"]

EXPOSE 80 22



#FROM ubuntu/apache2:latest
#COPY public/ /var/www/html
#COPY config/apache.conf /etc/apache2/apache2.conf
#EXPOSE 80