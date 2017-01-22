import java.awt.*;
import javax.swing.*;
import java.util.*;
import javax.sound.midi.*;

class Dr{


 String name; 
 ArrayList<JCheckBox>  chkBoxList = new ArrayList<JCheckBox>();

String[] instrNames = {"Bass Drum", "Closed Hi-Hat", "Open Hi-Hat", "Acoustic Snare", "Crash Cymbal", "Hand Clap", "HighTom", "Low-mid Tom", "Marcas","Whistle","Low Conga", "Cowbell", "Vibraslap",  "High Bongo","High Agogo","Open Hi Conga" };
/*
String[] instrNames = {"Bass Drum", "Closed Hi-Hat", "Open Hi-Hat", "Acoustic Snare", "Crash Cymbal", "Hand Clap", "HighTom", "Low-mid Tom"};*/

Sequencer seqr;
Sequence seq;
Track trk;
   

public void makeOpenWindow(){
JFrame f= new JFrame("Virtual Drumset");
f.setVisible(true);
f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
f.setSize(500,400);
f.setLayout(new FlowLayout());

JLabel l =new  JLabel("Loading Components.. Please wait.. ");
f.add(l);
JProgressBar p = new JProgressBar(SwingConstants.HORIZONTAL,10,100);
f.add(p);
p.setStringPainted(true);

for(int i=0;i<101;i++){
try{
Thread.sleep(1800);
}catch(Exception ex){
}
p.setValue(i);
}
f.setVisible(false);
}

public void makeMainWindow(){

JFrame f2= new JFrame("Virtual Drumset2");
JPanel p = new JPanel();

ImageIcon i1 = new  ImageIcon("thumb_up.gif");

p.setLayout(new BoxLayout(p,BoxLayout.Y_AXIS));
f2.setVisible(true);
f2.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
f2.setSize(900,600);
//f2.setLayout();
JLabel l1= new JLabel("Enter your name please...");
//l1.setBounds(400,20,200,60);
p.add(l1);
JTextField t1=new JTextField(15);

//p.add(t1);

JButton b1=new  JButton("Done");
b1.setIcon(i1);
//b1.setBounds(440,200,70,40);
p.add(b1);

f2.add(p,BorderLayout.CENTER);


//b1.setVisible(true);

}


public static void main(String args[]){

Dr d= new Dr();
//Loading page
//d.makeOpenWindow();
//Main window
//d.makeMainWindow();

d.startGui();




}

public void startGui(){

   JFrame Frm = new JFrame(" Virtual Drumset ");
   ImageIcon aa = new  ImageIcon("dd1.PNG");
   //Frm.setIcon(aa);

   Frm.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
   BorderLayout layout = new BorderLayout();
   JPanel Pnl = new JPanel(new BorderLayout());
   Pnl.setBorder(BorderFactory.createEmptyBorder(20,20,20,20));
   Frm.add(Pnl);
   Frm.setVisible(true);
   Frm.setBounds(50,20,800,500);
   
    //cbList = new ArrayList<JCheckBox>();
    Box btnBox = new Box(BoxLayout.Y_AXIS);  //1 small container

   JButton start = new JButton("Play");
   start.setToolTipText("Starts playing the drum pattern(after values have been assigned) ...");
//   start.addActionListener(new MyStartListener());
   btnBox.add(start);

   JButton stop = new JButton("Stop");
   stop.setToolTipText("Stops the drum pattern being played...");
//   stop.addActionListener(new MyStopListener());
   btnBox.add(stop);

   JButton upTempo = new JButton("Tempo Up");
   upTempo.setToolTipText("Paces up the drum pattern currently playing...");
//   upTempo.addActionListener(new MyUpTempoListener());
   btnBox.add(upTempo);

   JButton downTempo = new JButton("Tempo Down");
   downTempo.setToolTipText("Drops the speed of drum pattern currently playing...");
//   downTempo.addActionListener(new MyDownTempoListener());
   btnBox.add(downTempo);

   JButton erase = new JButton("Clear Values");
   erase.setToolTipText(" Empty's d checkbox state...");  
//   downTempo.addActionListener(new MyEraseValuesListener());
   btnBox.add(erase);

//   btnBox.createVerticalStrut(10);


  Box instrBox=new Box(BoxLayout.Y_AXIS);  //2 small container
  for(int i=0;i<16;i++){
      instrBox.add(new Label(instrNames[i]));
  }

   JPanel beatBox=new JPanel(new FlowLayout());  //3 small container
   JLabel lb =new  JLabel(" Beats Selection area  ");
   beatBox.add(lb);

   JPanel status=new JPanel(new FlowLayout());  //4 small container
   JLabel lbs =new  JLabel(" Status Bar ");
   status.add(lbs);

   Pnl.add(BorderLayout.NORTH,beatBox);
   Pnl.add(BorderLayout.EAST,btnBox);
   Pnl.add(BorderLayout.WEST,instrBox);
   Pnl.add(BorderLayout.SOUTH,status);

   Frm.add(Pnl);
   GridLayout grid = new GridLayout(16,16);
   grid.setVgap(1);
   grid.setHgap(2);
   JPanel chkBoxPanel = new JPanel(grid);
   Pnl.add(BorderLayout.CENTER, chkBoxPanel);
   
   for(int i=0;i<256;i++){
     JCheckBox c = new JCheckBox();
     c.setSelected(false);
     chkBoxList.add(c);
     chkBoxPanel.add(c);
   }  

  Frm.pack(); //make forcefully visible+pack it near.
  
   setMidi();

}


public void setMidi(){
   try{
   seqr = MidiSystem.getSequencer();
   seqr.open();
   seq = new Sequence(Sequence.PPQ,4);
   trk = seq.createTrack();
   seqr.setTempoInBPM(120);
   }catch(Exception ex){ ex.toString(); }
}



}