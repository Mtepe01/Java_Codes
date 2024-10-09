import java.util.Scanner;
public class Main
{
    public static int Pow(int pow)
    {
        if(pow<1)
            return 1;
        else
            return 2*Pow(pow-1);
    }
    public static void main(String[] args)
    {
    String str;
        Scanner scan=new Scanner(System.in);
        System.out.println("Please enter the binary number");
        str=scan.nextLine();

        int size=str.length();
        char[] arr=new char[size];
        int count=0;
        StringBuilder a=new StringBuilder(str);
        a.reverse();
        String word= String.valueOf(a);
        arr=word.toCharArray();
            for (int i = 0; i < size; i++)
            {
                if (arr[i] == '1')
                {
                    count = count + Pow(i);
                }
            }

            System.out.println(count);
        }
    }
