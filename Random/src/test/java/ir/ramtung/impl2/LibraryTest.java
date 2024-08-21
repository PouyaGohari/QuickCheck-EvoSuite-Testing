package ir.ramtung.impl2;
import static org.junit.Assert.*;

import com.pholser.junit.quickcheck.Property;
import com.pholser.junit.quickcheck.runner.JUnitQuickcheck;
import ir.ramtung.sts01.LibraryException;
import com.pholser.junit.quickcheck.generator.InRange;
import jdk.internal.org.objectweb.asm.tree.MethodInsnNode;
import org.junit.runner.RunWith;
import static org.junit.Assume.*;
import java.util.*;

@RunWith(JUnitQuickcheck.class)
public class LibraryTest {
    @Property
    public void testAddStudentMember(String studentId, String studentName) {
        Library library = new Library();
        try {
            library.addStudentMember(studentId, studentName);
            assertEquals(library.getTotalPenalty(studentName), 0);
            try {
                library.addStudentMember(studentId, studentName);
                fail("Expected a DuplicateMemberEx to be thrown");
            } catch (LibraryException e) {
                assertEquals("the member has already added", e.getMessage());
            }
        } catch (LibraryException e) {
            if(studentId == null || studentId.isEmpty()){
                assertEquals("student id is empty", e.getMessage());
            }
            else{
                assertEquals("student name is empty", e.getMessage());
            }
        }
    }
    @Property
    public void testAddProfMember(String ProfName) {
        Library library = new Library();
        try {
            if(ProfName == null || ProfName.isEmpty()){
                library.addProfMember(ProfName);
                fail("Expected to throw empty");
            }
            else{
                library.addProfMember(ProfName);
                try {
                    library.addProfMember(ProfName);
                }catch (LibraryException e){
                    assertSame("the member has already added", e.getMessage());
                }
            }
        }catch (LibraryException e){
            if(ProfName.isEmpty()){
                assertEquals("prof name is empty", e.getMessage());
            }
        }
    }
    @Property
    public void testAddProfMember2(String ProfName) {
        assumeTrue(!ProfName.isEmpty());
        Library library = new Library();
        try {
            library.addProfMember(ProfName);
            try {
                library.addProfMember(ProfName);
            }catch (LibraryException e){
                assertSame("the member has already added", e.getMessage());
            }
        }catch (LibraryException e){
            assertEquals("prof name is empty", e.getMessage());
        }
    }
    @Property
    public void testAddBook(String title, int copies){
        Library library = new Library();
        try {
            if(title.isEmpty()){
                library.addBook(title, copies);
                fail("Empty title should not be allowed");
            }
            else{
                library.addBook(title, copies);
                try{
                    library.addBook(title, copies);
                    fail("Expected a DuplicateMemberEx to be thrown");
                }catch (LibraryException e){
                    assertEquals("the book has already added", e.getMessage());
                }
            }
        }catch(LibraryException e){
            if(title.isEmpty()) {
                assertEquals("book name is empty", e.getMessage());
            }
        }
    }
    @Property
    public void testAddBook2(String title, int copies){
        assumeTrue(!title.isEmpty());
        Library library = new Library();
        try {
            library.addBook(title, copies);
            try{
                library.addBook(title, copies);
                fail("Expected a DuplicateMemberEx to be thrown");
            }catch (LibraryException e) {
                assertEquals("the book has already added", e.getMessage());
            }
        }catch(LibraryException e){
            assertEquals("book name is empty", e.getMessage());
        }
    }
    @Property
    public void testAddReference(String title, int copies){
        Library library = new Library();
        try {
            if(title.isEmpty()){
                library.addReference(title, copies);
                fail("title with an empty string is not allowd");
            }
            else {
                library.addReference(title, copies);
                try{
                    library.addReference(title, copies);
                    fail("Expected a DuplicateMemberEx to be thrown");
                }catch (LibraryException e){
                    assertEquals("the reference has already added", e.getMessage());
                }
            }
        }catch(LibraryException e){
            if(title.isEmpty()) {
                assertEquals("reference name is empty", e.getMessage());
            }
        }
    }
    @Property
    public void testAddReference2(String title, int copies){
        assumeTrue(!title.isEmpty());
        Library library = new Library();
        try {
            library.addReference(title, copies);
            try{
                library.addReference(title, copies);
                fail("Expected a DuplicateMemberEx to be thrown");
            }catch (LibraryException e){
                assertEquals("the reference has already added", e.getMessage());
            }
        }catch(LibraryException e){
            assertEquals("reference name is empty", e.getMessage());
        }
    }
    @Property
    public void testAddMagazine(String title, int yr, int num, int copies){
        Library library = new Library();
        try {
            if(title.isEmpty() || yr < 0 || num < 0){
                library.addMagazine(title, yr, num, copies);
                fail("Expect to throw exception");
            }
            else{
                library.addMagazine(title, yr, num, copies);
                try{
                    library.addMagazine(title, yr, num, copies);
                    fail("Expected a DuplicateMemberEx to be thrown");
                }catch (LibraryException e){
                    assertEquals("the magazine has already added", e.getMessage());
                }
            }
        }catch (LibraryException e){
            if (title.isEmpty()) {
                assertEquals("magazine name is empty", e.getMessage());
            } else if (yr<=0) {
                assertEquals("magazine`s year is incorrect", e.getMessage());
            } else if (num <= 0) {
                assertEquals("magazine`s number is incorrect", e.getMessage());
            }else {
                fail("Unexpected exception: " + e.getMessage());
            }
        }
    }
    @Property
    public void testAddMagazine2(String title, int yr, int num, int copies){
        assumeTrue(!title.isEmpty());
        assumeTrue(yr > 0);
        assumeTrue(num > 0);
        Library library = new Library();
        try {
            library.addMagazine(title, yr, num, copies);
            try{
                library.addMagazine(title, yr, num, copies);
                fail("Expected a DuplicateMemberEx to be thrown");
            }catch (LibraryException e){
                assertEquals("the magazine has already added", e.getMessage());
            }
        }catch (LibraryException e){
            fail("Unexpected exception: " + e.getMessage());
        }
    }
    @Property
    public void testBurrow(String memberName, String documentTitle){
        Library library = new Library();
        try {
            if(memberName == null || memberName.isEmpty() || documentTitle == null || documentTitle.isEmpty()){
                library.borrow(memberName, documentTitle);
            }
            else{
                library.addStudentMember("123", memberName);
                library.addBook(documentTitle, 3);
                library.borrow(memberName, documentTitle);
                library.borrow(memberName, documentTitle);
                try {
                    library.borrow(memberName,documentTitle);
                    fail("Expected a CannotBorrowEx to be thrown for borrowing more than allowed");
                }catch (LibraryException e){
                    assertEquals("the member cant borrow any more", e.getMessage());
                }
                String profName = "Dr." + memberName;
                library.addProfMember(profName);
                String secondDocumentTitle = documentTitle + "2";
                library.addBook(secondDocumentTitle, 6);
                library.borrow(profName, secondDocumentTitle);
                library.borrow(profName, secondDocumentTitle);
                library.borrow(profName, secondDocumentTitle);
                library.borrow(profName, secondDocumentTitle);
                library.borrow(profName, secondDocumentTitle);
                try {
                    library.borrow(profName, secondDocumentTitle);
                    fail("Expected a CannotBorrowEx to be thrown for unavailable document");
                }catch (LibraryException e){
                    assertEquals("the member cant borrow any more", e.getMessage());
                }
                String thirdDocumentTitle = documentTitle + "3";
                String thirdProf = "Dr2." + memberName;
                library.addBook(thirdDocumentTitle, 3);
                library.addProfMember(thirdProf);
                library.borrow(thirdProf, thirdDocumentTitle);
                library.borrow(thirdProf, thirdDocumentTitle);
                library.borrow(thirdProf, thirdDocumentTitle);
                try {
                    library.borrow(thirdProf, thirdDocumentTitle);
                    fail("Expected a CannotBorrowEx to be thrown for unavailable document");
                }catch (LibraryException e) {
                    assertEquals("this book doesnt exist", e.getMessage());
                }
            }
        } catch (LibraryException e){
            if(memberName == null || memberName.isEmpty()){
                assertEquals("Cannot find document to borrow", e.getMessage());
            }else if(documentTitle == null || documentTitle.isEmpty()){
                assertEquals("Cannot find member to borrow", e.getMessage());
            }
        }
    }
    @Property
    /*
     * Can not extend on the same day
     * Can not extend a late loan
     * Can not extend loan more than two times for books and reference docs
     * Can not extend Magazine docs
     *
     */
    public  void testExtendBook(String memberName, String documentTitle, int copies, int days){
        Library library = new Library();
        try{
            if(memberName==null || memberName.isEmpty()){
                library.extend(memberName, documentTitle);
            }else{
                assumeTrue(documentTitle!=null && !documentTitle.isEmpty());
                assumeTrue(copies > 0);
                library.addProfMember(memberName);
                library.addBook(documentTitle, copies);
                library.borrow(memberName, documentTitle);
                try {
                    library.extend(memberName, documentTitle);
                    fail("Expected a CannotExtendEx to be thrown for extending on the same day");
                } catch (LibraryException e) {
                    assertEquals("cant extend", e.getMessage());
                }
            }
        }catch (LibraryException e){
            if(memberName==null || memberName.isEmpty()){
                assertEquals("cant extend", e.getMessage());
            }
        }
    }
    @Property
    public  void testExtendBook2(String memberName, String documentTitle, int copies,@InRange(min = "0", max = "20") int days){
        assumeTrue(!documentTitle.isEmpty());
        assumeTrue(copies > 0);
        assumeTrue(!memberName.isEmpty());
        Library library = new Library();
        try{
            library.addProfMember(memberName);
            library.addBook(documentTitle, copies);
            library.borrow(memberName, documentTitle);
            library.timePass(days);
            try {
                if(days > 10){
                    library.extend(memberName, documentTitle);
                    fail("Expected a CannotExtendEx to be thrown for extending a late loan");
                }
                else if(days > 0){
                    library.extend(memberName, documentTitle);
                }
            } catch (LibraryException e) {
                assertEquals("cant extend", e.getMessage());
            }
        }catch (LibraryException e){
            fail("Unexpected message " + e.getMessage());
        }
    }
    @Property
    public  void testExtendReference(String memberName, String documentTitle, int copies, int days){
        Library library = new Library();
        try{
            if(memberName==null || memberName.isEmpty()){
                library.extend(memberName, documentTitle);
            }else{
                assumeTrue(documentTitle!=null && !documentTitle.isEmpty());
                assumeTrue(copies > 0);
                library.addProfMember(memberName);
                library.addReference(documentTitle, copies);
                library.borrow(memberName, documentTitle);
                try {
                    library.extend(memberName, documentTitle);
                    fail("Expected a CannotExtendEx to be thrown for extending on the same day");
                } catch (LibraryException e) {
                    assertEquals("cant extend", e.getMessage());
                }
                library.timePass(days);
                try {
                    if(days > 5){
                        library.extend(memberName, documentTitle);
                        fail("Expected a CannotExtendEx to be thrown for extending a late loan");
                    }
                    else{
                        library.extend(memberName, documentTitle);
                    }
                } catch (LibraryException e) {
                    assertEquals("cant extend", e.getMessage());
                }
            }
        }catch (LibraryException e){
            if(memberName==null || memberName.isEmpty()){
                assertEquals("cant extend", e.getMessage());
            }
        }
    }
    @Property

    public void testExtendMoreThanTwo(String memberName, String documentTitle, @InRange(min = "1") int copies, boolean bookReference) {
        assumeTrue(memberName != null && !memberName.isEmpty());
        assumeTrue(documentTitle != null && !documentTitle.isEmpty());

        Library library = new Library();

        try {
            library.addProfMember(memberName);
            if (bookReference) {
                library.addBook(documentTitle, copies);
            } else {
                library.addReference(documentTitle, copies);
            }
            library.borrow(memberName, documentTitle);
            library.timePass(3);
            library.extend(memberName, documentTitle);
            library.timePass(4);
            library.extend(memberName, documentTitle);
            try {
                library.extend(memberName, documentTitle);
                fail("Expected a LibraryException to be thrown for extending more than twice");
            } catch (LibraryException e) {
                assertEquals("cant extend", e.getMessage());
            }
        } catch (LibraryException e) {
            fail("Unexpected exception: " + e.getMessage());
        }
    }
    @Property
    public void testExtendMagazine(String memberName, String documentTitle, int copies, int yr, int num){
        assumeTrue(memberName != null && !memberName.isEmpty());
        assumeTrue(documentTitle != null && !documentTitle.isEmpty());
        assumeTrue(copies > 0);
        assumeTrue(yr > 0);
        assumeTrue(num > 0);

        Library library = new Library();
        try{
            library.addMagazine(documentTitle, yr, num, copies);
            library.addProfMember(memberName);
            library.borrow(memberName, documentTitle);
            library.timePass(1);
            try{
                library.extend(memberName, documentTitle);
                fail("Expected a CannotExtendEx to be thrown for extending a magazine");
            }catch (LibraryException e){
                assertEquals("Magazine cannot be extended", e.getMessage());
            }
        }catch (LibraryException e){
            fail("Unexpected exception: " + e.getMessage());
        }
    }
    @Property
    public void testReturnDocument(String memberName, String documentTitle){
        assumeTrue(memberName!=null && !memberName.isEmpty());
        assumeTrue(documentTitle!=null && !documentTitle.isEmpty());
        Library library = new Library();
        try{
            library.returnDocument(memberName, documentTitle);
            library.addProfMember(memberName);
            library.addBook(documentTitle, 2);
            library.borrow(memberName, documentTitle);
            library.timePass(3);
            library.extend(memberName, documentTitle);
            library.timePass(4);
            library.returnDocument(memberName, documentTitle);
        } catch (LibraryException e) {
            fail("Unexpected exception: " + e.getMessage());
        }
    }
    @Property
    public void testGetTotalPenaltyForBook(String memberName,@InRange(min = "0", max = "50")int days, String title, int copies){
        Library library = new Library();
        int expectedPenalty;
        if(days <= 10){
            expectedPenalty = 0;
        }else if(days <= 10 + 7){
            expectedPenalty = (days - 10) * 2000;
        }else if(days <= 10 + 7 + 14){
            expectedPenalty = 7 * 2000 + (days - 17) * 3000;
        }else {
            expectedPenalty = 7 * 2000 + 14 * 3000 + (days -  31) * 5000;
        }
        try {
            assumeTrue(title != null && !title.isEmpty());
            assumeTrue(copies > 0);
            library.addProfMember(memberName);
            library.addBook(title, copies);
            library.borrow(memberName, title);
            library.timePass(days);
            assertEquals(expectedPenalty, library.getTotalPenalty(memberName));
        }catch (LibraryException e){
            fail("Unexpected exception: " + e.getMessage());
        }
    }
    @Property
    public void testGetTotalPenaltyForReference(String memberName,@InRange(min = "0", max = "50")int days, String title, int copies){
        Library library = new Library();
        int expectedPenalty;
        if (days <= 5){
            expectedPenalty = 0;
        }else if(days <= 8){
            expectedPenalty = (days - 5) * 5000;
        }else{
            expectedPenalty = 3 * 5000 + (days - 8) * 7000;
        }
        try {
            assumeTrue(title != null && !title.isEmpty());
            assumeTrue(copies > 0);
            library.addProfMember(memberName);
            library.addReference(title, copies);
            library.borrow(memberName, title);
            library.timePass(days);
            assertEquals(expectedPenalty, library.getTotalPenalty(memberName));
        }catch (LibraryException e){
            fail("Unexpected exception: " + e.getMessage());
        }
    }
    @Property
    public void testGetTotalPenaltyForMagazine(String memberName,@InRange(min = "0", max = "50")int days, String title, int copies,
                                               int yr, int num){
        Library library = new Library();
        int expectedPenalty;
        if(days <= 2){
            expectedPenalty = 0;
        }
        else {
            if(yr <= 1390){
                expectedPenalty = (days - 2) * 2000;
            }else{
                expectedPenalty = (days - 2) * 3000;
            }
        }
        try {
            assumeTrue(title != null && !title.isEmpty());
            assumeTrue(copies > 0);
            assumeTrue(yr > 0);
            assumeTrue(num > 0);
            library.addProfMember(memberName);
            library.addMagazine(title, yr, num, copies);
            library.borrow(memberName, title);
            library.timePass(days);
            assertEquals(expectedPenalty, library.getTotalPenalty(memberName));
        }catch (LibraryException e){
            fail("Unexpected exception: " + e.getMessage());
        }
    }
    @Property
    public void testAvailableTitles(Character c,@InRange(min = "0", max="3") int length, String title, int copies) {
        assumeTrue(c != null);
        assumeTrue(!title.isEmpty());
        assumeTrue(copies > 0);
        Library library = new Library();
        List<String> expected = new ArrayList<>();
        try {
            for (int i = 0; i < length; i++) {
                String repeatedC = String.join("", Collections.nCopies(i + 1, Character.toString(c)));
                String temp = title + repeatedC;
                library.addBook(temp, copies + i);
                expected.add(temp);
            }
            List<String> actual = library.availableTitles();
            assertEquals(length, actual.size());
            boolean allSame = true;
            for (int i = 0; i < length; i++) {
                if (!actual.contains(expected.get(i))) {
                    allSame = false;
                }
            }
            assumeTrue(allSame);
        } catch (LibraryException e) {
            fail("Nothing must be thrown :)))) ");
        }
    }
    @Property
    public void testTimePass(@InRange(min= "-100", max = "100") int days){
        Library library = new Library();
        try{
            library.timePass(days);
        }catch (LibraryException e){
            assertEquals("days cant be negative", e.getMessage());
        }
    }
}