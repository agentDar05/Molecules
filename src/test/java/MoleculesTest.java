class MoleculesTest{
    public static void main(String[] args){
        Molecules m = new Molecules();
        m.parse("C6O6H12Cl");
        System.out.println( m.get());
    }
}