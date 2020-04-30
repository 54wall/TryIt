// AIDLDemo.aidl
package pri.weiqiang.tryit;

// Declare any non-default types here with import statements

interface AIDLDemo {
    /**
     * Demonstrates some basic types that you can use as parameters
     * and return values in AIDL.
     */
    void basicTypes(int anInt, long aLong, boolean aBoolean, float aFloat,
            double aDouble, String aString);


  void studySkill(out Role role);

  void changeName(String newName, inout Role role);

  void createRole(in Role name);
}
