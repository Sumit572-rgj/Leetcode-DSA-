class Solution {
    public boolean sumGame(String num) {
        int total_length = num.length();
        int half = total_length/2;
        int lsum=0;
        int rsum=0;
        int lq=0;
        int rq=0;
        int difference=0;
        int questionDifference=0;
        for(int i=0;i<half;i++){
      if(num.charAt(i)=='?'){
        lq++;
      }
      else{
        lsum=lsum + num.charAt(i) - '0';
      }
        }
     for(int i = half; i < total_length; i++){
       if(num.charAt(i)== '?'){
        rq++;
       }
       else{
        rsum=rsum  + num.charAt(i) - '0';
       }
        difference = lsum - rsum;
        questionDifference = rq - lq;
    }
     return !(2 * difference == 9 * questionDifference && (lq + rq) % 2 == 0);
    }
     
}