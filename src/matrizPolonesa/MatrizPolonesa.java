package matrizPolonesa;
/**
 * Matriz Polonesa
 * @autor 17188, 17189
*/
public class MatrizPolonesa
{
	protected boolean[][] matriz;

// CONSTRUTOR
	/**
	 * Construtor sem parametro que inicializa a constante Matriz Polonesa
	*/
	public MatrizPolonesa()
	{
		this.matriz = new boolean[7][7];

		for(int c=0; c<=1; c++)
			for(int l=0; l<=6; l++)
				this.matriz[l][c] = false;

		this.matriz[0][2] = false;
		this.matriz[1][2] = true;
		this.matriz[2][2] = true;
		this.matriz[3][2] = true;
		this.matriz[4][2] = false;
		this.matriz[5][2] = false;
		this.matriz[6][2] = false;

		this.matriz[0][3] = false;
		this.matriz[1][3] = true;
		this.matriz[2][3] = true;
		this.matriz[3][3] = true;
		this.matriz[4][3] = false;
		this.matriz[5][3] = false;
		this.matriz[6][3] = false;

		this.matriz[0][4] = false;
		this.matriz[1][4] = true;
		this.matriz[2][4] = true;
		this.matriz[3][4] = true;
		this.matriz[4][4] = true;
		this.matriz[5][4] = true;
		this.matriz[6][4] = false;

		this.matriz[0][5] = false;
		this.matriz[1][5] = true;
		this.matriz[2][5] = true;
		this.matriz[3][5] = true;
		this.matriz[4][5] = true;
		this.matriz[5][5] = true;
		this.matriz[6][5] = false;

		this.matriz[0][6] = true;
		this.matriz[1][6] = true;
		this.matriz[2][6] = true;
		this.matriz[3][6] = true;
		this.matriz[4][6] = true;
		this.matriz[5][6] = true;
		this.matriz[6][6] = false;
	}
	/**
	 * Devolve o boolean que se encontra na linha e coluna passadas
	 * @param oper1 sera o operador que representa a linha da matriz
	 * @param oper2 sera o operador que representa a coluna da matriz
	 * @return boolean : o elemento da matriz que coresponde aos parametros passados
	 * @exception Exception lanca excecao se algum parametro estiver incorreto (caso não seja um operador)
	*/
	public boolean get(char oper1, char oper2) throws Exception
	{
		int linha;
		int coluna;

		switch (oper2) {
			case '(':
				return false;
				//todos simbolos da primeira coluna sao falsos
			case '^':
				return false;
				//todos simbolos da segunda coluna sao falsos
			case '*':
				coluna = 2;
				break;
			case '/':
				coluna = 3;
				break;
			case '+':
				coluna = 4;
				break;
			case '-':
				coluna = 5;
				break;
			case ')':
				coluna = 6;
				break;
			default:
				throw new Exception("Operador 2 invalido!");
		}

		switch (oper1) {
			case '(':
				linha = 0;
				break;
			case '^':
				linha = 1;
				break;
			case '*':
				linha = 2;
				break;
			case '/':
				linha = 3;
				break;
			case '+':
				linha = 4;
				break;
			case '-':
				linha = 5;
				break;
			case ')':
				return false;
				//todos simbolos da ultima linha sao falsos
			default:
				throw new Exception("Operador 1 invalido!");
		}

		return this.matriz[linha][coluna];
	}

// METODOS OBRIGATORIOS
	/**
	 * Metodo obrigatorio que retorna a matriz polonesa como String
	 * @return String : retorna a matriz com suas devidas linhas e colunas
	*/
	public String toString()
	{
		String ret = "{";

		for(int l=0; l<=6; l++)
			for(int c=0; c<=6; c++)
				ret += this.matriz[l][c] + ((c==6 && l==6)?"}":c==6?"\n\r":", ");

		return ret;
	}
	/**
	 * Metodo obrigatorio que retorna um int que representa o objeto (this)
	 * @return int : retorna o um numero de identificacao para a Matriz Polonesa
	*/
	public int hashCode()
	{
		int ret = 13;

		for(int l=0; l<=6; l++)
			for(int c=0; c<=6; c++)
				ret = ret*7 + new Boolean(this.matriz[l][c]).hashCode();

		return ret;
	}
	/**
	 * Metodo que retorna o this clonado
	 * @return Objetct : retorna uma instancia da Classe MatrizPolonesa igual ao this
	*/
	public boolean equals(Object obj)
	{
		if(obj==null)
			return false;
		if(obj==this)
			return true;

		if(!(obj instanceof MatrizPolonesa))
			return false;

		return true;
	}
}