import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        Tree orderTree = new Tree();
        boolean check = true;

        while (check) {
            System.out.println("\n1.Add orders\n2.Cancel Order\n3.Display Orders\n4.Exit");
            System.out.print("Choose an option: ");
            int choose = scan.nextInt();

            switch (choose) {
                case 1:
                    System.out.print("Enter the number of products: ");
                    int productCount = scan.nextInt();
                    scan.nextLine(); // Clear buffer

                    String[] productNames = new String[productCount];
                    int[] orderCounts = new int[productCount];

                    for (int i = 0; i < productCount; i++) {
                        System.out.print("Enter product name " + (i + 1) + ": ");
                        productNames[i] = scan.nextLine();
                        System.out.print("Enter order count for " + productNames[i] + ": ");
                        orderCounts[i] = scan.nextInt();
                        scan.nextLine(); // Clear buffer
                    }

                    orderTree.addMultipleOrders(productNames, orderCounts);
                    break;

                case 2:
                    System.out.print("Enter product name: ");
                    scan.nextLine(); // Clear buffer
                    String ProductName = scan.nextLine();
                    System.out.print("Enter order count to cancel: ");
                    int orderCount = scan.nextInt();
                    orderTree.cancelOrder(ProductName, orderCount);
                    break;

                case 3:
                    orderTree.OrderQuery();
                    break;

                case 4:
                    check = false;
                    break;

                default:
                    System.out.println("Invalid choice! Try again.");
            }
        }
        System.out.println("Exiting...");
        scan.close();

    }

}

class Node
{
    Node left, right;
    String ProductName;
    int orderCount;

    public Node(String ProductName, int orderCount)
    {
        this.ProductName = ProductName;
        this.orderCount = orderCount;
        this.left = null;
        this.right = null;
    }
}

class Tree
{
    Node root;

    public Tree()
    {
        this.root = null;
    }

    public void add(String ProductName, int orderCount)
    {
        root = AddOrderRecursive(root, ProductName, orderCount);

    }
// compare Product name with other product name where is avaible for the that product name it place to suitible place
    private Node AddOrderRecursive(Node node, String ProductName, int orderCount)
    {
        if (node == null)
        {
            return new Node(ProductName, orderCount);
        }
        if (ProductName.compareTo(node.ProductName) < 0)
        {
            node.left = AddOrderRecursive(node.left, ProductName, orderCount);
        }
        else if (ProductName.compareTo(node.ProductName) > 0)
        {
            node.right = AddOrderRecursive(node.right, ProductName, orderCount);
        }
        else
        {
            node.orderCount += orderCount;
        }
        return node;
    }
//Take the order in a array after that use add function
    public void addMultipleOrders(String[] productNames, int[] orderCounts)
    {
        for (int i = 0; i < productNames.length - 1; i++)
        {
            //The order sort the order according to  alphabeth
            for (int j = i + 1; j < productNames.length; j++)
            {
                if (productNames[i].compareTo(productNames[j]) > 0)
                {
                //If the product names unsorted we sort for that we swap the productname and counts
                    String tempName = productNames[i];
                    productNames[i] = productNames[j];
                    productNames[j] = tempName;

                    int tempCount = orderCounts[i];
                    orderCounts[i] = orderCounts[j];
                    orderCounts[j] = tempCount;
                }
            }
        }
        //The loop add the multiorders
        for (int i = 0; i < productNames.length; i++)
        {
            add(productNames[i], orderCounts[i]);
        }
    }

    public void cancelOrder(String ProductName, int orderCount)
    {
        if (orderCount <= 0)
        {
            System.out.println("Order count must be greater than 0!");
            return;
        }
        root = cancelOrderRecursive(root, ProductName, orderCount);
    }
//If any order cancel the function find the node if node amount > canceled order of amount just subcribe the that amount otherwise remove the node
    private Node cancelOrderRecursive(Node node, String ProductName, int orderCount)
    {
        if (node == null)
        {
            System.out.println("The " + ProductName + " cannot be found");
            return null;
        }
        if (ProductName.compareTo(node.ProductName) < 0)
        {
            node.left = cancelOrderRecursive(node.left, ProductName, orderCount);
        }
        else if (ProductName.compareTo(node.ProductName) > 0)
        {
            node.right = cancelOrderRecursive(node.right, ProductName, orderCount);
        }
        else
        {
            node.orderCount -= orderCount;
            if (node.orderCount <= 0)
            {
                System.out.println("Amount of " + ProductName + " is zero. Removing " + ProductName + ".");
                return RemoveNode(node);
            }
        }
        return node;
    }
//Find and remove the node also it is connected to removed node's child to new parent of they  by FindNewSmallRoot
    private Node RemoveNode(Node node)
    {
        if (node.left == null)
        {
            return node.right;
        }
        if (node.right == null)
        {
            return node.left;
        }
        Node newSmallRoot = FindNewSmallRoot(node.right);
        node.ProductName = newSmallRoot.ProductName;
        node.orderCount = newSmallRoot.orderCount;
        node.right = cancelOrderRecursive(node.right, newSmallRoot.ProductName, newSmallRoot.orderCount);
        return node;
    }
// The Find new small root function try find the new root.
// The new root hasn't any child and it is obey the tree rule we have a two option
// One of these left child's the biggest child its mean the righest child of the leftsubtree
// Otherone is the leftest child of the right subtree
// In that function chosed right subtree's leftest child
    private Node FindNewSmallRoot(Node node)
    {
        if(node.left==null)
        {
            return node.left;
        }
        else
            return FindNewSmallRoot(node.left);
    }
//Display  the order 
    public void OrderQuery()
    {
        if (root == null)
        {
            System.out.println("No orders found!");
        }
        else
        {
            System.out.println("root");
            inOrderTraverse(root,"  ");
        }
    }
//For the display by in order traverse
// Firstly get the left node after that root finally get root node
private void inOrderTraverse(Node node, String str)
{
    if (node != null)
    {
        System.out.println(str+"|__"+node.ProductName+"("+node.orderCount+")");
        inOrderTraverse(node.left, str+"     "); 
        inOrderTraverse(node.right, str+"    ");
    }
}

}