USE `mentalaid`;

INSERT INTO `question` (`creator_id`, `title`, `description`) VALUES
  (2, 'Unable to access local network IP from docker container',
   'Running macOS and docker ubuntu on it, I am unable to ping my local network''s IP addresses. Below is the network configuration of my docker container. I can ping my host machine''s IP address but I am unable to access other IP addresses on the local network.'),
  (2, 'Restore “Unknown” Partition To ntfs',
   'I''m using Fedora.

I don''t know why my NTFS (/dev/sda3) partition is changed to "Unknown". I can''t read this partition. My important files are located in there and I have to change it back to ntfs without losing data. '),
  (3, 'Connect wireless Xbox 360 controller with wire',
   'My wireless controller does not work. Is it possible to connect it with a wire without batteries?

Thank you in advamce.'),
  (5, 'Disable automatic line break in LibreOffice Writer',
   'In LibreOffice Writer, in a table cell, there is the text "abcdef/+ghijklm". The cell is not wide enough to contain the whole text, so there is an automatic line break after the "/" even though "abcdef/+ghi" would fit into the line. How can I prevent the automatic line break?

 I know how to insert a protected hyphen or a protected space, but how can I disable the automic line break in general? I didn''t find any formatting option and no solution on the web.'),
  (5, 'how copy a folder that have signs in it name in windows 7',
   'i bought a software disk. some folders have sign in those name. like $ and etc. i cant change folders name because they are related to eachother and are systemic.it was possible to copy files on windows xp. but when i want to copy on my main pc (win 7), nothing happening when i press paste.

 '),
  (5, 'How to autostart OpenVPN (client) on Ubuntu 12.04 CLI?',
   'I have an *.ovpn file that works if I type in: sudo openvpn filename.ovpn.

 Now I would like to start up OpenVPN when I boot the computer. It''s a headless version of Ubuntu 12.04 64-bit if that matters.

 I copied filename.ovpn to /etc/openvpn, but it''s not starting, even if I run: service openvpn start.

 How can I do this?'),
  (5, 'How to remove the end of the “s” in partial “_error_messages”',
   'How to remove the end of the "s" in partial "_error_messages".

 <% if event.errors.any? %>
 <div id="errorExplanation">
   <h2> В форме обнаружено <%= pluralize(event.errors.count, "ошибки") %>:</h2>
   <ul>
   <% event.errors.full_messages.each do |msg| %>
     <li><%= msg %></li>
   <% end %>
   </ul>
 </div>
 <% end %>
 '),
  (5, 'MySQLi error messages encoding',
   'I''ve got DB in UTF8 and everything if fine except one thing - when I write to file $mysqli->connect_error, it is written in cp1251, not utf8.

 if ($mysqli->connect_errno)
     file_put_contents(''error.log'', ''Ошибка: ''.$db->connect_error, FILE_APPEND);
 In file I can see ''Ошибка: '', but then goes text in wrong encoding. How can I set utf8 for MySQLi error messages?

 '),
  (5, 'How to emulate LOST UPDATE in Postgresql?',
   '/* TRANSACTION 1*/
 USE dbmcw;
 DECLARE @sal int = 0;
 BEGIN TRAN;
 SELECT @sal = salary
 FROM dbmcw.lost_update_demonstration
 WHERE worker_id = 1;
 SET @sal = @sal + 10;
 WAITFOR DELAY ''00:00:05.000'';
 UPDATE dbmcw.lost_update_demonstration
 SET salary = @sal
 WHERE ProductID = 1;
 SELECT salary
 FROM dbmcw.lost_update_demonstration
 WHERE worker_id = 1;
 COMMIT TRAN;

 /* TRANSACTION 2*/
 USE dbmcw;
 DECLARE @sal int = 0;
 BEGIN TRAN;
 SELECT @sal = salary
 FROM dbmcw.lost_update_demonstration
 WHERE worker_id = 1;
 SET @sal = @sal + 20;
 UPDATE dbmcw.lost_update_demonstration
 SET salary = @sal
 WHERE worker_id = 1;
 SELECT salary
 FROM dbmcw.lost_update_demonstration
 WHERE worker_id = 1;
 COMMIT TRAN;
 When I run this code, Postgresql is giving this error: ОШИБКА: ошибка синтаксиса (примерное положение: "USE"). Translation: "ERROR: syntax error(location : "USE"). LINE 2: USE dbmcw;

 How to solve this problem?'),
  (6, 'Длина string - Delphi',
   'Подскажите пожалуйста.
 Имеется:
 s: string.
 Как написать, что если длина данного s равна 6 символам то Ок.'),
  (5, 'Длина текста в TEdit',
   'Собственно сабж. Каким более коротким путем, можно узнать длину введеного текста в TEdit''е?'),
  (7, 'Delphi Длинная арифметика',
   '1) Составить программу деления числа a на число b, если a, b — многозначные числа.
 2) Вычислить 100! + 2^100.
 '),
  (7, 'Длинная арифметика Delphi',
   'Дорогие, форумчане! Я пишу код по алгоритму шифрования Эль-Гамаля. Прошу вас помочь мне с расчетом длинных чисел. Скажите хотя бы что делать. Гуглом я пользоваться умею. Жду вразумительный ответ.'),
  (7, 'Битовая арифметика - Delphi',
   'Всем привет, может кто ниб поедлится любой прогой по переводу из одного бита в другой!!пожалуйста!
 '),
  (7, 'Длинная арифметика: деление (Делфи желательно)',
   'Люди, вот тут не могу решить одну задачку. Алгоритм решения задачи есть. А именно конкретный пункт реализовать не могу. Пусть имеется массив из цифр. Как его разделить на какое-то число (не длинное), чтобы получить новый массив после деления и остаток?
 Я не прошу за вас составить такой код, я прошу если он уже у вас есть - дать мне его. Конечно, можно попытаться и изобрести самому такой код, но зачем изобретать велосипед, если он уже изобретен (только я не знаю где :)). Ведь если кому-то нужна процедура QSort - никто же её вручную не изобретает, а пользуется уже известным вызубренным кодом.
 Так у меня таже проблема. С длинной арифметикой я могу складывать, вычитать и перемножть числа. А вот делить не умею. Причем мне и не надо делить "длинное" число на "длинное". Мне нужно его разделить на короткое, в результате чего получиться "длинный" число и короткий остаток.
 Надеюсь, поможете.
 1 апреля уже в Новосибирск надо будет ехать на Всероссийскую олимпиаду, надеюсь, если найду ответ, мне это там как-то поможет.
 Заранее спасибо! :)');