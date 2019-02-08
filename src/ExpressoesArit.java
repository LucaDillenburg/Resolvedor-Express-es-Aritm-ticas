import fila.*;
import pilha.*;
import matrizPolonesa.*;
import java.io.*;
import java.util.*;

public class ExpressoesArit implements Cloneable
{
	protected String expressao;
	protected Pilha<String>  pilha;
	protected Fila<String>   fila;
	protected double         resultado;
	protected boolean        resolveu = false;

	/**
	* Construtor que recebe a operacao aritmetica como parametro
	* @param exp Operacao Aritmetica numa String
	* @exeption Exception Lanca excecao se a string passada por parametro for nula
	*/
	public ExpressoesArit(String exp) throws Exception
	{
		if(exp==null || exp=="")
			throw new Exception("Expressão fornecida nula!");
		this.expressao = exp;
	}

	/**
	* Metodo que coloca o '*' e '0' onde esta subentendido, e 'x' para '*'
	* ex1: (2-1)3 => (2-1)*3
	* ex2: 2^(-5)
	*/
	public void modificarExp()
	{
		for(int i=0; i<=this.expressao.length()-1; i++)
			switch (this.expressao.charAt(i))
			{
				case ')':
					if(i<this.expressao.length()-1 && !this.ehOperador(this.expressao.charAt(i+1)+""))
						try
						{
							int n = Character.getNumericValue(expressao.charAt(i+1));

							this.expressao = this.expressao.substring(0, i+1) + "*" +
								this.expressao.substring(i+1, expressao.length());
							i++;
						}catch(Exception erro)
						{} //se o caracter depois de ')' nao for um numero ou nao houver um caracter depois, nao faz nada
					break;
				case '(':
					if(i>0 && !this.ehOperador(this.expressao.charAt(i-1)+""))
						try
						{
							int n = Character.getNumericValue(expressao.charAt(i-1));

							this.expressao = this.expressao.substring(0, i) + "*" +
								this.expressao.substring(i, expressao.length());
							i++;
						}catch(Exception erro)
						{} //se o caracter antes de '(' nao for um numero ou nao houver um caracter antes, nao faz nada
					break;
				case ':': //outro simbolo para dividido
					this.expressao = this.expressao.substring(0, i) + "/" +
						this.expressao.substring(i+1, expressao.length());
					break;
				case 'x': //outro simbolo para vezes
				case 'X': //outro simbolo para vezes
					this.expressao = this.expressao.substring(0, i) + "*" +
						this.expressao.substring(i+1, expressao.length());
					break;
				case '-':
					if(i==0 || this.expressao.charAt(i-1)=='(')
						this.expressao = this.expressao.substring(0, i) + "0" +
								this.expressao.substring(i, expressao.length());
			}
	}

	protected boolean ehOperador(String op)
	{
		return op.equals("+") || op.equals("-") || op.equals("*") ||
				op.equals("/") || op.equals("^") || op.equals("(") ||
				op.equals(")");
	}

	/**
	* Metodo que coloca tudo na fila na ordem correta
	* @throws Exception Lanca excecao se na expressao dada estiver
	* faltando um '(' ou se houver um caracter invalido
	*/
	public void primeraEtapa() throws Exception
	{
		// PRIMEIRA ETAPA:
		this.pilha  = new Pilha();
		this.fila   = new Fila();
		MatrizPolonesa matriz = new MatrizPolonesa();

		StringTokenizer quebrador = new StringTokenizer(
									expressao, "+-*/^()", true);

		String token;
		while(quebrador.hasMoreTokens())
		{
			token = quebrador.nextToken();

			if(this.ehOperador(token))
			// verifica se eh operador
			{
				if(token.equals(")"))
				{
					try
					{
						// passa tudo da pilha para a fila ateh achar o '('

						String op;
						boolean achou=false;

						while(!achou)
						// se nao achar e acabar os elementos da pilha,
						// dara erro e significa que esta faltando o '('
						{
							op = this.pilha.getElemento();
							this.pilha.desempilhe();

							if(op.equals("("))
								achou=true;
							else
								this.fila.enfileire(op);
						}
					}catch(Exception erro)
					{
						throw new Exception("Expressao aritmetica invalida: faltando '('!");
					}
				}else
				{
					String op;
					char arg1;
					char arg2 = token.charAt(0);

					while(!this.pilha.vazia())
					{
						arg1 = this.pilha.getElemento().charAt(0);

						if(!matriz.get(arg1, arg2))
							break;

						op = this.pilha.getElemento();
						this.pilha.desempilhe();

						this.fila.enfileire(op);
					}

					this.pilha.empilhe(token);
				}
			}else
			{
				try
				{
					float x = Float.parseFloat(token);
				}catch(Exception erro)
				{
					throw new Exception("Expressao aritmetica invalida: '"+token+"' operador ou operando desconhecido!");
				}

				this.fila.enfileire(token);
			}
		}

		// move tudo para a fila
		String op;
		while(!this.pilha.vazia())
		{
			op = this.pilha.getElemento();
			this.pilha.desempilhe();
			this.fila.enfileire(op);

			// se ainda tem o '(' aqui eh porque esta faltando um ')'
			if(op.equals("("))
				throw new Exception("Expressao aritmetica invalida: faltando ')'!");
		}
	}

	/**
	* Resolve as operacoes na ordem que estava na fila e adiciona ao resoultado
	* @exeption Exception Lanca excecao se houver operadores ou operando a mais ou a menos, ou divisao por zero
 	*/
	public void segundaEtapa() throws Exception
	{
		// SEGUNDA ETAPA:
		String op;
		double op1;
		double op2;
		char   oper;
		double res;

		while(!this.fila.vazia())
		{
			op = fila.getElemento();
			this.fila.desenfileire();

			if(this.ehOperador(op))
			{
				oper = op.charAt(0);

				if(this.pilha.vazia())
					throw new Exception("Expressao aritmetica invalida: faltando operando!");
				op2  = Float.parseFloat(this.pilha.getElemento());
				this.pilha.desempilhe();

				if(pilha.vazia())
					throw new Exception("Expressao aritmetica invalida: faltando operando!");
				op1  = Float.parseFloat(this.pilha.getElemento());
				this.pilha.desempilhe();

				// resolve as operacoes
				switch(oper)
				{
					case '^':
						res = Math.pow(op1, op2);
						break;
					case '*':
						res = op1*op2;
						break;
					case '/':
						if(op2==0)
							throw new Exception("Expressao aritmetica invalida: divisao por zero!");
						res = op1/op2;
						break;
					case '+':
						res = op1+op2;
						break;
					default:
						res = op1-op2;
				}

				this.pilha.empilhe(Double.toString(res));
			}else
				this.pilha.empilhe(op);
		}

		this.resultado = Double.parseDouble(this.pilha.getElemento());
		this.pilha.desempilhe();
		if(!this.pilha.vazia())
			throw new Exception("Expressao aritmetica invalida: faltando operadores!");
		this.resolveu = true;
	}

	/**
	* Metodo que devolve o resultado da expressao
	* @return double Retorna o resultado da expressao aritmetica
	* @exeption Exception Lanca excecao se a expressao ainda nao foi resolvida
	*/
	public double getResultado() throws Exception
	{
		if(!this.resolveu)
			throw new Exception("Os metodos para resolver a expressao nao foram realizados!");

		return this.resultado;
	}

//metodos obrigatorios
	/**
	* Metodo obrigatorio que retorna a instancia em uma String
	* @return String : Formato= Expressao: , Plha: , Fila: ,
	* Resultado: (se nao resolveu ainda, escreve que a expressao nao foi resolvida)
	*/
	public String toString()
	{
		String ret = "EXPRESSAO: "+this.expressao+"\n";
		ret += "Pilha: "+this.pilha+"\n";
		ret += "Fila: "+this.fila+"\n";
		if(this.resolveu)
			ret += "RESULTADO: "+this.resultado;
		else
			ret += "Expressao nao resolvida.";

		return ret;
	}

	/**
	 * Metodo obrigatorio que retorna um int que representa o objeto (this)
	 * @return int : retorna o um numero "unico" para cada Expressao Aritmetica diferente
	*/
	public int hashCode()
	{
		int ret = 13;
		ret = ret*7 + this.expressao.hashCode();
		ret = ret*7 + this.pilha.hashCode();
		ret = ret*7 + this.fila.hashCode();
		ret = ret*7 + new Double(this.resultado).hashCode();
		ret = ret*7 + new Boolean(this.resolveu).hashCode();

		return ret;
	}

	/**
	* Metodo obrigatorio que verifica se o this e o outro objeto passado como parametro sao iguais
	* @return boolean : retorna false se os objetos forem diferentes e true se forem iguais
	* @param obj Objeto a ser comparado com o this
	*/
	public boolean equals(Object obj)
	{
		if(obj==null)
			return false;
		if(this==obj)
			return true;

		if(!(obj instanceof ExpressoesArit))
			return false;

		ExpressoesArit exp = (ExpressoesArit)obj;

		if(!this.expressao.equals(exp.expressao) || !this.pilha.equals(exp.pilha) ||
			!this.fila.equals(exp.fila) || this.resolveu!=exp.resolveu || this.resultado!=exp.resultado)
			return false;

		return true;
	}

	/**
	 * Construtor que copia todos as variaveis do parametro dado para o this
	 * @param modelo eh o modelo que tera as variaveis copiadas para o this (eh o molde para o this)
	 * @ Exception lanca excecao caso o modelo seja nulo
	*/
	public ExpressoesArit(ExpressoesArit modelo) throws Exception
	{
		if(modelo==null)
			throw new Exception("Expressao fornecida nula!");

		this.expressao = modelo.expressao;
		this.pilha = new Pilha(modelo.pilha);
		this.fila = new Fila(modelo.fila);
		this.resultado = modelo.resultado;
		this.resolveu = modelo.resolveu;
	}

	/**
	 * Metodo que retorna o this clonado
	 * @return Objetct : retorna uma instancia da Classe ExpressoesArit igual ao this
	*/
	public Object clone()
	{
		ExpressoesArit ret = null;

		try
		{
			ret = new ExpressoesArit(this);
		}catch(Exception erro)
		{}

		return ret;
	}
}