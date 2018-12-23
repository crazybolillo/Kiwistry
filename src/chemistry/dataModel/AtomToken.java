package chemistry.dataModel;

/**
 * Used when parsing a molecule. It stores the atom's symbol and the amount
 * of atoms found inside the molecule formula.
 * @author https://github.com/AntonioBohne
 */
public class AtomToken {
    
    private String symbol;
    private int amount;
    
    /**
     * Creates a new Atom token and assigns it both the symbol and amount
     * passed trough the parameters.
     * @param symbol Atom's symbol.
     * @param amount Amount of atoms of this type.
     */
    public AtomToken(String symbol, int amount){
        this.symbol = symbol;
        this.amount = amount;
    }
    
    /**
     * Creates a new AtomToken and parses the formula passed trough the
     * parameters to separate the symbol from the amount.
     * @param formula Atom's formula. For example, H2. 
     * @throws NumberFormatException If the syntax is not valid an exception
     * will be thrown. The only way for the syntax to be incorrect would
     * be to include characters after the number that states the amount 
     * of atoms.
     */
    public AtomToken(String formula) throws NumberFormatException {
        for(int x = 0; x < formula.length(); x++){
            if(Character.isDigit(formula.charAt(x))){
                this.amount = Integer.parseInt(formula.substring(x));
                this.symbol = formula.substring(0, x);
                return;
            }
        }
        this.symbol = formula;
        this.amount = 1;
    }

    /**
     * Returns the symbol of this atom. 
     * @return Atom's symbol.
     */
    public String getSymbol() {
        return symbol;
    }

    /**
     * Returns the amount of atoms of this type.
     * @return Amount of atoms.
     */
    public int getAmount() {
        return amount;
    }
}
