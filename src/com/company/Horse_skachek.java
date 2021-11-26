package com.company;

import java.util.Scanner;
import java.util.List;
import java.util.ArrayList;


public class Horse_skachek//основной класс
{
    public static void main(String[] args)
    {
        Scanner in = new Scanner(System.in);
        System.out.print("Сколько лошадей учавствуют в забеге?: ");
        int n = in.nextInt();//ввод n
        startHorses(n);//Запуск метода "startHorses"
    }

    public static void startHorses(int number_of_horses)//Запуск number_of_horses
    {
        Object lock = new Object();//Объявление объекта lock
        List<Thread> horses = new ArrayList<Thread>(number_of_horses);//Массив horses из number_of_horses потоков.
        String number;//номер лошади
        for (int i = 1; i < number_of_horses + 1; i++)
        {
            number = i < 10 ? ("0" + i) : "" + i;
            Thread horse_i = new Thread(new HorseThread(lock, "Лошадь_" + number));//Объявление потока horse_i как объекта класса HorseThread.
            horses.add(horse_i);//Добавление потока horse_i в массив horses.
        }
        for (int i = 0; i < number_of_horses; i++)
        {
            horses.get(i).start();//Запуск потока с индексом i из массива horses.
        }
    }
}

class HorseThread implements Runnable//Класс HorseThread использует интерфейс Runnable.
{
    private Object lock;//Поле lock.
    private String name;//Поле name.

    public HorseThread(Object lock, String name)//Конструктор HorseThread.
    {
        this.lock = lock;
        this.name = name;
    }

    @Override
    public void run()//Запустится после запуска потока методом start().
    {
        synchronized (lock)
        {
            String s = "";
            for (int i = 0; i < 10001; i++)
            {
                if (i == 10000)
                {
                    s = " прибежала!";
                    System.out.println(name + s);//Вывод имени лошади ("Лошадь_" + номер_лошади).
                    try  // При отсутствии исключений:
                    {
                        Thread.sleep(1000);//Приостановка потока на 1 секунду
                    }
                    catch (InterruptedException e)//В случае исключения:
                    {
                        e.printStackTrace();//Строка в которой метод вызвал исключение
                    }
                    lock.notify();//Продолжение работы того потока, который находится в режиме ожидания (у которого ранее был вызван метод wait()).
                    try//При отсутствии исключений:
                    {
                        lock.wait(1000);//Приостановка потока на 1 секунду или установка в ожидание
                    }
                    catch (InterruptedException e)//В случае исключения:
                    {
                        e.printStackTrace();//Строка в которой метод вызвал исключение
                    }
                }
            }
        }
    }
}
