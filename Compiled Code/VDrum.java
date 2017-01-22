/******************************************************************
/
/                         > VIRTUAL DRUMSET : SOURCE CODE <
/                                The Ultimate Drum Machine
/
******************************************************************/
//  Coded By Hargovind Singh 

import java.awt.*;
import javax.swing.*;
import java.io.*;
import javax.sound.midi.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.event.*;

//*****************************************************************
//  CLASS NAME :  VDrum
//  DETAILS :  Contains all d methods & listener classes 4 d GUI.
//*****************************************************************
public class VDrum implements Serializable{
  
    JPanel mainPanel;
    JFrame theFrame;
   ArrayList<JCheckBox>  checkboxList;
   String userName;
   Sequencer sequencer;
   Sequence sequence;
   Sequence mySequence = null;
   Track track;

String[] instrumentNames = {"Bass Drum", "Closed Hi-Hat", "Open Hi-Hat", "Acoustic Snare", "Crash Cymbal", "Hand Clap", "HighTom", "Low-mid Tom", "Marcas","Whistle","Low Conga", "Cowbell", "Vibraslap",  "High Bongo","High Agogo","Open Hi Conga" };

int[] instruments = {35,42,46,38,49,39,50,47,70,72,64,56,58,60,67,63};

//static block
static{
System.out.println("\n\t\t\t Loading Please Wait . . . .");
float ctr=1.0f;
for(float j=1;j<50000.0f;j++)
for(float i=1;i<500.0f;i++)ctr=ctr*i;

}


//*****************************************************************  
//   FUNCTION : main()
//   SIGNIFICANCE : Execution of d program begins from here .
//*****************************************************************
public static void main(String[] args)throws Exception{
  
   System.out.println("\nHello, Welcome to The Ultimate Drum Machine ");   
   
try{

   new VDrum().startUp();      //Using anonymous object.

}catch(Exception e){
e.printStackTrace();

}finally{



}



} //close main method 
	

public void startUp(){
   
  
/*Still working on networking & i/o, d cyber vesion*/
   System.out.println("creating GUI....");


  new VDrum().buildGUI();





} //close startUp()


//*****************************************************************  
//   FUNCTION : buildGUI()
//   SIGNIFICANCE : Builds d frame, buttons(4), labels(16), checkboxes(256).
//*****************************************************************
public void buildGUI(){

   theFrame = new JFrame("                                        >>> Virtual Drum Set <<<  ");

//System.out.println(theFrame.isFullScreenSupported()); 

   theFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
   BorderLayout layout = new BorderLayout();
   JPanel background = new JPanel(layout);
   background.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));

   checkboxList = new ArrayList<JCheckBox>();
   Box buttonBox = new Box(BoxLayout.Y_AXIS);

   JButton start = new JButton("Start");
   start.addActionListener(new MyStartListener());
   buttonBox.add(start);
   ImageIcon ii = new ImageIcon("Startbutton.png");
   start.setIcon(ii);

   JButton stop = new JButton("Stop");
   stop.addActionListener(new MyStopListener());
   buttonBox.add(stop);
   ImageIcon ii2 = new ImageIcon("stop_button.png");
   stop.setIcon(ii2);


   JButton upTempo = new JButton("Tempo Up");
   upTempo.addActionListener(new MyUpTempoListener());
   buttonBox.add(upTempo);
   ImageIcon ii3 = new ImageIcon("up.PNG");
   upTempo.setIcon(ii3);


   JButton downTempo = new JButton("Tempo Down");
   downTempo.addActionListener(new MyDownTempoListener());
   buttonBox.add(downTempo);
   ImageIcon ii4 = new ImageIcon("down.PNG");
   downTempo.setIcon(ii4);



  Box nameBox=new Box(BoxLayout.Y_AXIS);
  for(int i=0;i<16;i++){
      nameBox.add(new Label(instrumentNames[i]));
  }

   

   GridLayout grid = new GridLayout(16,16);
   grid.setVgap(1);
   grid.setHgap(2);
   mainPanel = new JPanel(grid);
   background.add(BorderLayout.CENTER, mainPanel);

 JPanel beatBox=new JPanel(new FlowLayout());  //3 small container
   JLabel lb =new  JLabel(" Beats Selection area  ");
   beatBox.add(lb);

 JPanel status=new JPanel(new FlowLayout());  //4 small container
   JLabel lbs =new  JLabel(" Status Bar ");
   status.add(lbs);

 JButton erase = new JButton("Clear Values");
   erase.setToolTipText(" Empty's d checkbox state...");  
   downTempo.addActionListener(new MyEraseValuesListener());
   buttonBox.add(erase);

start.setToolTipText("Starts playing the drum pattern(after values have been assigned) ...");
stop.setToolTipText("Stops the drum pattern being played...");
upTempo.setToolTipText("Paces up the drum pattern currently playing...");
downTempo.setToolTipText("Drops the speed of drum pattern currently playing...");

   background.add(BorderLayout.EAST,buttonBox);
   background.add(BorderLayout.WEST,nameBox);
   background.add(BorderLayout.NORTH,beatBox);
   background.add(BorderLayout.SOUTH,status);
   theFrame.add(background);

   for(int i=0;i<256;i++){
     JCheckBox c = new JCheckBox();
     c.setSelected(false);
     checkboxList.add(c);
     mainPanel.add(c);
   }  //End of Loop
  
   setUpMidi();

   theFrame.setBounds(50,50,300,300);
   theFrame.pack();
   theFrame.setVisible(true);
}  //close buildGUI()


//*****************************************************************  
//   FUNCTION : setUpMidi()
//   SIGNIFICANCE : Makes d sequence & hence a track
//*****************************************************************
public void setUpMidi(){
   try{
   sequencer = MidiSystem.getSequencer();
   sequencer.open();
   sequence = new Sequence(Sequence.PPQ,4);
   track = sequence.createTrack();
   sequencer.setTempoInBPM(60);
   } catch(Exception e){ e.printStackTrace();}
} //close setUpMidi()

public void eraseValues(){

 for(int i=0;i<256;i++){
     JCheckBox c = new JCheckBox();
     c.setSelected(false);
     checkboxList.add(c);
     mainPanel.add(c);

    JFrame ff= new JFrame();
    JPanel pp = new JPanel(new FlowLayout());
    JButton Fine = new JButton("Fine"); 
    ImageIcon ic = new ImageIcon("java-duke-logo6.PNG");
    Fine.setIcon(ic);
    ff.setVisible(true);
    ff.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
  
   } 

}
//*****************************************************************  
//   FUNCTION : buildTrackAndStart()
//   SIGNIFICANCE : The event handling method
//*****************************************************************
public void buildTrackAndStart(){

int[] trackList = null; //holds each instrument

sequence.deleteTrack(track);
track = sequence.createTrack();

for(int i=0;i<16;i++){
  trackList = new int[16];
  int key  = instruments[i];
 
  for(int j=0;j<16;j++){

     JCheckBox  jc = (JCheckBox) checkboxList.get(j + (16*i));
     if(jc.isSelected())
    { 
       trackList[j]=key;
     } 
     else{ trackList[j] = 0; }  //as dis slot should b empty in d track
   }//close of inner loop

   makeTracks(trackList);
   track.add(makeEvent(176,1,127,0,16)); 
}//close of outer loop

track.add(makeEvent(192,9,1,0,15));  //we always go 2 full 16 beats
try{
   sequencer.setSequence(sequence);
   sequencer.setLoopCount(sequencer.LOOP_CONTINUOUSLY);
   sequencer.start();
   sequencer.setTempoInBPM(60);  
} catch(Exception ep){ep.printStackTrace();}
}  //close buildTrackAndStrart()


public class MyStartListener implements ActionListener{
  public void actionPerformed(ActionEvent a){
      buildTrackAndStart();
  }  //close actionPerformed
}  //close inner class


public class MyStopListener implements ActionListener{
  public void actionPerformed(ActionEvent a){
      sequencer.stop();
  }  //close actionPerformed
}  //close inner class


public class MyEraseValuesListener implements ActionListener{
  public void actionPerformed(ActionEvent a){
      
eraseValues();
  }  //close actionPerformed
}  //close inner class



public class MyUpTempoListener implements ActionListener{
  public void actionPerformed(ActionEvent a){
     float tempoFactor = sequencer.getTempoFactor();
     sequencer.setTempoFactor((float) (tempoFactor*1.03));
  }  //close actionPerformed
}  //close inner class


public class MyDownTempoListener implements ActionListener{
  public void actionPerformed(ActionEvent a){
     float tempoFactor = sequencer.getTempoFactor();
     sequencer.setTempoFactor((float) (tempoFactor*.97));
  }  //close actionPerformed
}  //close inner class


public void makeTracks(int[] list){

     for(int i=0; i<16;i++){
      int key = list[i];
      if( key != 0){     
       track.add(makeEvent(144,9,key,100,i));
       track.add(makeEvent(128,9,key,100,i+1));
      }
   }  //close loop
}   //close makeTracks()


public MidiEvent makeEvent(int comd, int chan, int one,int two, int tick){
       MidiEvent event = null;
       try{
            ShortMessage a = new ShortMessage();
            a.setMessage(comd, chan, one, two);
            event = new MidiEvent(a,tick) ;

            }  catch( Exception e){     System.out.println("Exception=>"+e);       }
        return event;    
 }  //close makeEvent

}  //close class...Yeah its dats it.