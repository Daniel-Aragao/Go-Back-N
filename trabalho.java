import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

public class trabalho {

	/**
	 * @param args
	 */
	public static void main(String[] args) throws IOException{
		String fileName = args[0];
		System.out.println("Lendo: " + fileName);

		ArrayList<String> linhas = Leitor.getLines(fileName);
		
		GoBackN gbn = new GoBackN(linhas);

		gbn.start();

		Escritor.writeLines(gbn.getSaida());
	}

}

class Leitor{
	public static ArrayList<String> getLines(String path) throws IOException{
		ArrayList <String> retorno = new ArrayList<String>();

		BufferedReader br = new BufferedReader(new FileReader(path));
		try{
			String line = br.readLine();
		
			while (line != null) {
				retorno.add(line);
				line = br.readLine();
			}
		}catch(FileNotFoundException e){
			System.out.println("File Not Found");
		}finally{
			br.close();
		}
		return retorno;
	}
}

class Escritor{
	public static void writeLines(ArrayList<String> saida) throws IOException{
		PrintWriter pw = new PrintWriter(new FileWriter("saida"));
		int length = saida.size();
		for(int i = 0; i < length; i++){
			String linha = saida.get(i);
			pw.write(linha);
			pw.println();
			// if(i+1 < length){
			// }
		}
		pw.close();
	}
}

class GoBackN{
	ArrayList<String> Linhas;
	ArrayList<String> Saida;

	public GoBackN(ArrayList<String> linhas){
		Linhas = linhas;
		Saida = new ArrayList<String>();
	}

	public ArrayList<String> getSaida(){
		return Saida;
	}

	public void start(){
		Remetente r = new Remetente();
		Destinatario d = new Destinatario();

		for (String linha : Linhas) {
			try{

				if(!linha.equals("timeout") && !linha.isEmpty()){
					String[] parts = linha.split(" ");
					if(parts[0].equals("A")){
						r.act(parts);
					}else{
						d.act(parts);
					}
				}
			}catch(Exception e){
				System.out.println("Erro na linha: "+linha);
				e.printStackTrace();
			}
		}
		Saida = d.getSaida();
	}
}
class Remetente{
	public void act(String[] parts){

	}
}
class Destinatario{
	private int expectedseqnum;
	private ArrayList<String> Saida;

	public Destinatario(){
		Saida = new ArrayList<String>();
		expectedseqnum = 0;
	}

	public void act(String[] parts){
		int pkt = Integer.parseInt(parts[2].split("pkt")[1]);
		if(pkt == expectedseqnum){
			recebe(parts[2]);
		}else{
			descarta(parts[2]);
		}
	}

	public void recebe(String s){
		String add = "B E ACK" + expectedseqnum;
		add += " entrega " + s;
		expectedseqnum++;
		Saida.add(add);
	}

	public void descarta(String s){
		String add = "";
		int ack = expectedseqnum - 1;
		if(ack >= 0){
			// ack = 0;
			add = "B E ACK" + ack + " ";
		}
		add += "descarta " + s;
		Saida.add(add);
	}

	public ArrayList<String> getSaida(){
		return Saida;
	}
}