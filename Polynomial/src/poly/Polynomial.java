package poly;

import java.io.IOException;
import java.util.Scanner;

public class Polynomial {
	
	public static Node read(Scanner sc) 
	throws IOException {
		Node poly = null;
		while (sc.hasNextLine()) {
			Scanner scLine = new Scanner(sc.nextLine());
			poly = new Node(scLine.nextFloat(), scLine.nextInt(), poly);
			scLine.close();
		}
		return poly;
	}
	
	public static Node add(Node poly1, Node poly2) {
		Node cur1 = poly1; 
		Node cur2 = poly2; 
		Node poly3 = null;
		
		if (poly1 == null && poly2 == null) {
			return null;
		} else if (poly1 == null) {
			return poly2;
		} else if (poly2 == null) {
			return poly1;
		}
		
		while (cur1 != null || cur2 != null) {
			
			Node result = null;

			if (cur1 == null) {
				result = new Node(cur2.term.coeff, cur2.term.degree, poly3);
				poly3 = result;
				cur2 = cur2.next;
			} else if (cur2 == null) {
				result = new Node(cur1.term.coeff, cur1.term.degree, poly3);
				poly3 = result;
				cur1 = cur1.next;
			} else if (cur1.term.degree == cur2.term.degree) {
				int coeff = (int)(cur1.term.coeff + cur2.term.coeff);
				if (coeff == 0) {
					cur1 = cur1.next;
					cur2 = cur2.next;
				} else {
					result = new Node(coeff, cur1.term.degree, poly3);
					poly3 = result;
					cur1 = cur1.next;
					cur2 = cur2.next;
				}
			} else if (cur1.term.degree < cur2.term.degree) {
				result = new Node(cur1.term.coeff, cur1.term.degree, poly3);
				poly3 = result;
				cur1 = cur1.next;
			} else if (cur2.term.degree < cur1.term.degree) {
				result = new Node (cur2.term.coeff, cur2.term.degree, poly3);
				poly3 = result;
				cur2 = cur2.next;
			}

		}		
		//sort
		Node cur3 = poly3;
		Node polyfinal = null;
		while (cur3 != null) {
				if (polyfinal == null) {
					polyfinal = new Node(cur3.term.coeff, cur3.term.degree, null);
					cur3 = cur3.next;
				} else {
					Node result = new Node(cur3.term.coeff, cur3.term.degree, polyfinal);
					polyfinal = result;
					cur3 = cur3.next;
				}	
		}
		return polyfinal;
	}
	
	public static Node multiply(Node poly1, Node poly2) {
		if ((poly1 == null && poly2 == null) || poly1 == null || poly2 == null) {
			return null;
		} 
		
		Node cur1 = poly1;
		Node poly3 = null;
		while (cur1 != null) {
			Node cur2 = poly2;
			while (cur2 != null) {
				float coeff = cur2.term.coeff * cur1.term.coeff;
				int degree = cur2.term.degree + cur1.term.degree;
				if (coeff == 0) {
					cur2 = cur2.next;
				} else {
					Node result = new Node(coeff, degree, poly3);
					poly3 = result;
					cur2 = cur2.next;
				}
			}
			cur1 = cur1.next;
		}
		//sort
		Node findmaxmin = poly3;
		int max = 0; 
		if (poly3 != null) {
			int min = poly3.term.degree;
			while (findmaxmin != null) {
				if (findmaxmin.term.degree > max) {
					max = findmaxmin.term.degree;
				}
				if (findmaxmin.term.degree < min) {
					min = findmaxmin.term.degree;
				}
				findmaxmin = findmaxmin.next;
			}			
			
			Node polyfinal = null;
			while (max >= min) {
				Node cur3 = poly3;
				float coeff = 0;
				while (cur3 != null) {
					if (cur3.term.degree == max) {
						coeff += cur3.term.coeff;
					}
					cur3 = cur3.next;
				}
				Node result = new Node(coeff, max, polyfinal);
				polyfinal = result;
				max--;
			}
			return polyfinal;
		} else {
			return null;
		}
	}
		
	public static float evaluate(Node poly, float x) {
		float sum = 0;
		Node cur = poly;
		
		if (poly == null) {
			return 0;
		}
		
		while (cur != null) {
			float a = (float) Math.pow(x, cur.term.degree);
			a = a*cur.term.coeff;
			sum += a;
			cur = cur.next;
		}
		
		return sum;
	}
	
	
	public static String toString(Node poly) {
		if (poly == null) {
			return "0";
		} 
		
		String retval = poly.term.toString();
		for (Node current = poly.next ; current != null ;
		current = current.next) {
			retval = current.term.toString() + " + " + retval;
		}
		return retval;
	}	
}