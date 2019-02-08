import pilha.*;
import fila.*;
import matrizPolonesa.*;
import java.io.*;

/**
* Classe que resolve qualquer operacao aritmetica dada
* @author 17188, 17189
* @version 1.0
*/
public class ResolvedorExpressoesAritmeticas
{
	/**
	 * Main: resolve as operacoes e mostra na tela, incluindo os erros
	*/
	public static void main(String[] args)
	{
		String resp="S";
		do
		{
			BufferedReader teclado = new BufferedReader(new InputStreamReader
											 			(System.in));
			try
			{
				System.out.println("Digite uma expressao aritmetica:");

				String expressao = teclado.readLine();

				ExpressoesArit resolvedor = new ExpressoesArit(expressao);

				resolvedor.modificarExp(); //coloca os '*' onde o mesmo está subentendido
				resolvedor.primeraEtapa();
				resolvedor.segundaEtapa();
				double resultado = resolvedor.getResultado();

				System.out.println();
				System.out.println();
				System.out.println(expressao+" = "+resultado);

			}catch(Exception erro)
			{
				// Nao tem o que fazer com esses erros a nao ser mostra-los ao usuario
				System.err.println();
				System.err.println();
				System.err.println(erro);
			}


			boolean continuar;
			do
			{
				System.out.println();
				System.out.println();
				System.out.println("Deseja realizar outra operacao? (S/N)");
				try
				{
					resp = teclado.readLine();
					continuar = !resp.equals("s") && !resp.equals("S") && !resp.equals("n") && !resp.equals("N");
				}catch(Exception erro)
				{
					continuar = true;
					// Se der algum erro no readLine, ele pergunta denovo
				}

				//escreve que as opcoes foram invalidas se ele nao escreveu "n", "N", "s" ou "S"
				if(continuar)
					System.out.println("Sua resposta nao esta dentro das opcoes dadas.");
			}while(continuar);

			System.out.println();
			System.out.println();
		}
		while(resp.equals("s") || resp.equals("S"));
	}
}