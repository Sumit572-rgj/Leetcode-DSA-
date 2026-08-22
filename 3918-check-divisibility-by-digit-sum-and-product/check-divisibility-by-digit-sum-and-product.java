class Solution {
    public boolean checkDivisibility(int n) {
        int original = n;
        int sum=0;
        int totalsum = 0;
        int product=1;
        while(n>0){
            int digit = n%10;
            sum = sum + digit;
            product = product * digit;
            n=n/10;
          totalsum = sum + product;
        }
        if(original % totalsum == 0){
            return true;
        }
        else{
            return false;
        }
    }
}