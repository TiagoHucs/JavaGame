import java.util.ArrayList;

public class Space {
	
	ArrayList<Star> estrelas = new ArrayList<Star>();
	
	public Space(){
		
		for(int i = 0 ; i < 100;i++){	
			Star estrela = new Star();
			estrelas.add(estrela);
		}
		
	}

	public ArrayList<Star> getEstrelas() {
		return estrelas;
	}

}


