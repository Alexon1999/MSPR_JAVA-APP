FROM ubuntu:latest

RUN apt-get update && apt-get install openssh-server sudo vim -y
RUN apt-get install -y apache2

# ssh configuration file : /etc/ssh/ssh_config
# authorize SSH connection with root account
RUN sed -i '/^#/!s/PermitRootLogin .*/PermitRootLogin yes/' /etc/ssh/sshd_config
# create new user
RUN useradd -rm -d /home/ubuntu -s /bin/bash -g root -G sudo -u 1000 test
# username:password
RUN  echo 'test:test' | chpasswd

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