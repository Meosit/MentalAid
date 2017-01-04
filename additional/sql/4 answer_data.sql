USE `mentalaid`;

INSERT INTO `answer` (`question_id`, `creator_id`, `text`) VALUES (
  15, /* Длинная арифметика: деление (Делфи желательно) */
  5,
  '#define ARRAY_SIZE 10

int main()
{
// Десятичная система исчисления (в байте одна десятичная цифра)
 BYTE ДЕЛИМОЕ [ ARRAY_SIZE ];
 BYTE ДЕЛИТЕЛЬ;
 BYTE ЧАСТНОE[ ARRAY_SIZE ];
 BYTE ОСТАТОК;

// Инициализация ДЕЛИМОЕ, ДЕЛИТЕЛЬ
// ...

// Деление
 ОСТАТОК = 0;
 for (int i = ARRAY_SIZE - 1; i >= 0; i--)
 {
   int temp = 10 * ОСТАТОК + ДЕЛИМОЕ [ i ];
   ЧАСТНОE[ i ] = (BYTE)(temp / ДЕЛИТЕЛЬ) ;
   ОСТАТОК = (BYTE)(temp % ДЕЛИТЕЛЬ) ;
 }

return 0;
}'
);

SELECT sleep(1);

INSERT INTO `answer` (`question_id`, `creator_id`, `text`) VALUES (
  15, /* Длинная арифметика: деление (Делфи желательно) */
  2,
  'Это подробно описано в книге С. Окулова "Программирование в алгоритмах"... Попробуй найти, а перепечатывать - это с ума можно сойти...'
);

SELECT sleep(1);

INSERT INTO `answer` (`question_id`, `creator_id`, `text`) VALUES (
  8, /* MySQLi error messages encoding */
  2,
  'The documentation is available here http://php.net/manual/en/mysqli.set-charset.php

OOP Approach

$mysqli->set_charset("cp1251");
Manual approuch

mysqli_set_charset($connection, "utf8");'
);

SELECT sleep(1);

INSERT INTO `answer` (`question_id`, `creator_id`, `text`) VALUES (
  7, /* How to remove the end of the “s” in partial “_error_messages” */
  7,
  'You need to do...

pluralize(event.errors.count, "ошибка", "ошибки")
It will use the singular term if one, and the plural term otherwise.'
);

SELECT sleep(1);

INSERT INTO `answer` (`question_id`, `creator_id`, `text`) VALUES (
  4, /* Disable automatic line break in LibreOffice Writer */
  3,
  'Apparently the behavior to break on punctuation cannot be disabled. My recommendation, which you seem to already be aware of, is to add U+2060 after the slash to keep it from breaking.

Or, the unicode character U+0338 is a slash that will not break lines. Note that this is a combining diacritic, which means that it attaches to the letter "f" to make a single character, rather than two.

Another method is to go to Format -> Paragraph -> Text Flow, and check Hyphenation - Automatically. This seems to do the trick except that it adds a hyphen. There is more about hyphenation here.

The only other way is simply to add a manual line break after the "i".

A discussion is here: https://forum.openoffice.org/en/forum/viewtopic.php?f=7&t=5160'
);

SELECT sleep(1);

INSERT INTO `answer` (`question_id`, `creator_id`, `text`) VALUES (
  6, /* How to autostart OpenVPN (client) on Ubuntu 12.04 CLI? */
  5,
  'It would be nice to have a un hacker way of doing it, but this will have to do for now.

1) Create file myopenvpn in /etc/init.d/

nano /etc/init.d/myopenvpn
2) Insert into myopenvpn and save:

# OpenVPN autostart on boot script

start on runlevel [2345]
stop on runlevel [!2345]

respawn

exec /usr/sbin/openvpn --status /var/run/openvpn.client.status 10 --cd /etc/openvpn --config /etc/openvpn/client.conf --syslog openvpn
SOURCE: http://www.hackerway.ch/2012/12/11/how-to-auto-start-openvpn-client-in-debian-6-and-ubuntu-12-04/#comment-79'
);

SELECT sleep(1);

INSERT INTO `answer` (`question_id`, `creator_id`, `text`) VALUES (
  6, /* How to autostart OpenVPN (client) on Ubuntu 12.04 CLI? */
  1,
  '1) Ubuntu is derived from Debian. Debian has a manual page about this: https://wiki.debian.org/OpenVPN#Auto-start
2) They both have a README file installed with the openvpn package. That file says, how and where to place configs for autostart to work.
Configs should be /etc/openvpn/*.conf, and by default "openvpn" service tries to run them all. If you put five .conf-files there, you''ll get five VPNs running when you restart openvpn service. /etc/default/openvpn could be used to select which vpns to execute by default.'
);

