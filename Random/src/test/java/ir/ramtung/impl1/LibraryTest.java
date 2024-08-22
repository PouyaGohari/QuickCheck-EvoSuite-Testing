package ir.ramtung.impl1;
import static org.junit.Assert.*;

import com.pholser.junit.quickcheck.Property;
import com.pholser.junit.quickcheck.runner.JUnitQuickcheck;
import com.pholser.junit.quickcheck.generator.InRange;
import org.junit.runner.RunWith;

import static org.junit.Assume.*;

import java.util.*;


@RunWith(JUnitQuickcheck.class)
public class LibraryTest {

    @Property
    public void validBookTitle(String title) {
        try {
            Book b = new Book(title);
            assertEquals(title, b.getTitle());
            assertEquals(b.loanDuration(), 10);
            assertTrue(b.isTitled(title));
        } catch (InvalidArgumentEx e) {
            if (title == null || title.isEmpty()) {
                assertEquals("Documents with empty title are not allowed", e.getMessage());
            } else {
                fail("Unexpected exception: " + e.getMessage());
            }
        }
    }
    @Property
    public void testPenaltyCalculationBook(@InRange(min = "0", max = "35")int days) {
        try {
            Book b = new Book("Test Book");
            int expectedPenalty;
            if(days <= 7){
                expectedPenalty = days * 2000;
            } else if (days <= 21) {
                expectedPenalty = 7 * 2000 + (days - 7) * 3000;
            }
            else{
                expectedPenalty = 7 * 2000 + (21-7) * 3000 + (days - 21)*5000;
            }
            int actualPenalty = b.penaltyFor(days);
            assertEquals(expectedPenalty, actualPenalty);
        }
        catch (InvalidArgumentEx e){
            fail("Unexpected exception: " + e.getMessage());
        }
    }
    @Property
    public void validReference(String title){
        try {
            Reference reference = new Reference(title);
            assertEquals(title, reference.getTitle());
            assertEquals(reference.loanDuration(), 5);
            assertTrue(reference.isTitled(title));
        } catch (InvalidArgumentEx e) {
            if (title == null || title.isEmpty()) {
                assertEquals("Documents with empty title are not allowed", e.getMessage());
            } else {
                fail("Unexpected exception: " + e.getMessage());
            }
        }
    }
    @Property
    public void testPenaltyCalculationReference(@InRange(min = "0", max = "35")int days){
        try {
            Reference ref = new Reference("Test Book");
            int expectedPenalty;
            if(days <= 3){
                expectedPenalty = days * 5000;
            } else {
                expectedPenalty = 3 * 5000 + (days-3) * 7000;
            }
            int actualPenalty = ref.penaltyFor(days);
            assertEquals(expectedPenalty, actualPenalty);
        }
        catch (InvalidArgumentEx e){
            fail("Unexpected exception: " + e.getMessage());
        }
    }
    @Property
    public void validMagazine(String title, int yr, int num){
        try{
            if(title == null || title.isEmpty() || yr <= 0 || num <= 0){
                Magazine m = new Magazine(title, yr, num);
                fail("Expected an InvalidArgumentEx to be thrown");
            }
            else{
                Magazine m = new Magazine(title, yr, num);
                assertEquals(title, m.getTitle());
                assertEquals(yr, m.year);
                assertEquals(num, m.number);
                assertTrue(m.isTitled(title));
                assertEquals(m.loanDuration(), 2);
            }
        }
        catch (InvalidArgumentEx e) {
            if(title==null || title.isEmpty()){
                assertEquals("Documents with empty title are not allowed", e.getMessage());
            } else if (yr<=0) {
                assertEquals("Magazine with zero or negative publication year", e.getMessage());
            } else if (num <= 0) {
                assertEquals("Magazine with zero or negative number", e.getMessage());
            }else {
                fail("Unexpected exception: " + e.getMessage());
            }
        }
    }
    @Property
    public void testPenaltyCalculationMagazine(@InRange(min = "-20", max = "2000") int yr, int days){
        try {
            Magazine m = new Magazine("This is test mag", yr, 10);
            if(yr > 0){
                int expectedPenalty;
                if(yr < 1390){
                    expectedPenalty = days * 2000;
                }
                else{
                    expectedPenalty = days * 3000;
                }
                int actualPenalty = m.penaltyFor(days);
                assertEquals(expectedPenalty, actualPenalty);
            }
        }catch (InvalidArgumentEx e) {
            if(yr <= 0) {
                assertEquals("Magazine with zero or negative publication year", e.getMessage());
            }else {
                fail("Unexpected exception: " + e.getMessage());
            }
        }
    }
    @Property
    public void testAddStudentMember(String studentId, String studentName) {
        Library library = new Library();
        try {
            library.addStudentMember(studentId, studentName);
            assertEquals(library.getTotalPenalty(studentName), 0);
            try {
                library.addStudentMember(studentId, studentName);
                fail("Expected a DuplicateMemberEx to be thrown");
            } catch (DuplicateMemberEx e) {
                assertEquals("Member with the same name exists", e.getMessage());
            }
        } catch (InvalidArgumentEx | DuplicateMemberEx e) {
            if(studentName == null || studentName.isEmpty()){
                assertEquals("Empty member name not allowed", e.getMessage());
            }
            else{
                assertEquals("Empty student ID is not allowed", e.getMessage());
            }
        }
    }
    @Property
    public void testAddProfMember(String ProfName) {
        Library library = new Library();
        try {
            library.addProfMember(ProfName);
            try {
                library.addProfMember(ProfName);
            }catch (DuplicateMemberEx e){
                assertSame("Member with the same name exists", e.getMessage());
            }
        }catch (InvalidArgumentEx | DuplicateMemberEx e){
            assertEquals("Empty member name not allowed", e.getMessage());
        }
    }
    @Property
    public void testAddBook(String title, int copies){
        Library library = new Library();
        try {
            library.addBook(title, copies) ;
            try{
                library.addBook(title, copies);
                fail("Expected a DuplicateMemberEx to be thrown");
            }catch (DuplicateDocumentEx e){
                assertEquals("Document with the same title exists", e.getMessage());
            }
        }catch(DuplicateDocumentEx | InvalidArgumentEx e){
            if(title == null || title.isEmpty()) {
                assertEquals("Documents with empty title are not allowed", e.getMessage());
            }else if(copies <= 0){
                assertEquals("Negative or zero copies of a document cannot be added", e.getMessage());
            }
        }
    }
    @Property
    public void testAddReference(String title, int copies){
        Library library = new Library();
        try {
            library.addReference(title, copies);
            try{
                library.addReference(title, copies);
                fail("Expected a DuplicateMemberEx to be thrown");
            }catch (DuplicateDocumentEx e){
                assertEquals("Document with the same title exists", e.getMessage());
            }
        }catch(DuplicateDocumentEx | InvalidArgumentEx e){
            if(title == null || title.isEmpty()) {
                assertEquals("Documents with empty title are not allowed", e.getMessage());
            }else if(copies <= 0){
                assertEquals("Negative or zero copies of a document cannot be added", e.getMessage());
            }
        }
    }
    @Property
    public void testAddMagazine(String title, int yr, int num, int copies){
        Library library = new Library();
        try {
            library.addMagazine(title, yr, num, copies);
            try{
                library.addMagazine(title, yr, num, copies);
                fail("Expected a DuplicateMemberEx to be thrown");
            }catch (DuplicateDocumentEx e){
                assertEquals("Document with the same title exists", e.getMessage());
            }
        }catch (DuplicateDocumentEx | InvalidArgumentEx e){
            if(title==null || title.isEmpty()){
                assertEquals("Documents with empty title are not allowed", e.getMessage());
            } else if (yr<=0) {
                assertEquals("Magazine with zero or negative publication year", e.getMessage());
            } else if (num <= 0) {
                assertEquals("Magazine with zero or negative number", e.getMessage());
            }else if (copies <= 0){
                assertEquals("Negative or zero copies of a document cannot be added", e.getMessage());
            }else {
                fail("Unexpected exception: " + e.getMessage());
            }
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
                }catch (CannotBorrowEx e){
                    assertEquals("Cannot borrow more documents", e.getMessage());
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
                }catch (CannotBorrowEx e){
                    assertEquals("Cannot borrow more documents", e.getMessage());
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
                }catch (CannotBorrowEx e) {
                    assertEquals("Document is not available to borrow", e.getMessage());
                }
            }
        } catch (InvalidArgumentEx | DuplicateMemberEx | DuplicateDocumentEx| CannotBorrowEx e){
            if(memberName == null || memberName.isEmpty()){
                assertEquals("Cannot find document to borrow", e.getMessage());
            }else if(documentTitle == null || documentTitle.isEmpty()){
                assertEquals("Cannot find member to borrow", e.getMessage());
            }
        }
    }
    @Property
    public void testBurrowMultipleMember(String memberName, String documentTitle){
        Library library = new Library();
        try {
            if(memberName == null || memberName.isEmpty() || documentTitle == null || documentTitle.isEmpty()){
                library.borrow(memberName, documentTitle);
            }
            else{
                library.addStudentMember("123", memberName);
                String secondMember = "Dr." + memberName;
                String thirdMember = "Dr.2" + memberName;
                String forthMember = "Dr.3" + memberName;
                library.addProfMember(secondMember);
                library.addProfMember(thirdMember);
                library.addProfMember(forthMember);
                library.addBook(documentTitle, 3);
                library.borrow(memberName, documentTitle);
                library.borrow(secondMember, documentTitle);
                library.borrow(thirdMember, documentTitle);
                try {
                    library.borrow(forthMember, documentTitle);
                }catch (CannotBorrowEx e){
                    assertEquals("Document is not available to borrow", e.getMessage());
                }
            }
        } catch (InvalidArgumentEx | DuplicateMemberEx | DuplicateDocumentEx| CannotBorrowEx e){
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
                } catch (CannotExtendEx e) {
                    assertEquals("Cannot extend on the same day borrowed", e.getMessage());
                }
                library.timePass(days);
                try {
                    if(days > 10){
                        library.extend(memberName, documentTitle);
                        fail("Expected a CannotExtendEx to be thrown for extending a late loan");
                    }
                    else{
                        library.extend(memberName, documentTitle);
                    }
                } catch (CannotExtendEx e) {
                    assertEquals("Cannot extend a late loan", e.getMessage());
                }
            }
        }catch (InvalidArgumentEx | CannotExtendEx | DuplicateMemberEx | DuplicateDocumentEx | CannotBorrowEx e){
            if(memberName==null || memberName.isEmpty()){
                assertEquals("The document is not in member's loan", e.getMessage());
            }
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
                } catch (CannotExtendEx e) {
                    assertEquals("Cannot extend on the same day borrowed", e.getMessage());
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
                } catch (CannotExtendEx e) {
                    assertEquals("Cannot extend a late loan", e.getMessage());
                }
            }
        }catch (InvalidArgumentEx | CannotExtendEx | DuplicateMemberEx | DuplicateDocumentEx | CannotBorrowEx e){
            if(memberName==null || memberName.isEmpty()){
                assertEquals("The document is not in member's loan", e.getMessage());
            }
        }
    }
    @Property
    public void testExtendMoreThanTwo(String memberName, String documentTitle, int copies, boolean bookReference){
        Library library = new Library();
        try{
            if(memberName==null || memberName.isEmpty()){
                library.extend(memberName, documentTitle);
            }else{
                assumeTrue(documentTitle!=null && !documentTitle.isEmpty());
                assumeTrue(copies > 0);
                library.addProfMember(memberName);
                if(bookReference){
                    library.addBook(documentTitle, copies);
                }else{
                    library.addReference(documentTitle, copies);
                }
                library.borrow(memberName, documentTitle);
                library.timePass(3);
                library.extend(memberName, documentTitle);
                library.timePass(4);
                library.extend(memberName, documentTitle);
                try{
                    library.extend(memberName, documentTitle);
                }catch (CannotExtendEx e){
                    assertEquals("Already extended twice", e.getMessage());
                }
            }
        }catch (InvalidArgumentEx | CannotExtendEx | DuplicateMemberEx | DuplicateDocumentEx | CannotBorrowEx e){
            if(memberName==null || memberName.isEmpty()){
                assertEquals("The document is not in member's loan", e.getMessage());
            }
        }
    }
    @Property
    public void testExtendMagazine(String memberName, String documentTitle, int copies, int yr, int num){
        Library library = new Library();
        try{
            if(memberName==null || memberName.isEmpty()){
                library.extend(memberName, documentTitle);
            }else{
                assumeTrue(documentTitle!=null && !documentTitle.isEmpty());
                assumeTrue(copies > 0);
                assumeTrue(yr > 0);
                assumeTrue(num > 0);
                library.addMagazine(documentTitle, yr, num, copies);
                library.addProfMember(memberName);
                library.borrow(memberName, documentTitle);
                library.timePass(1);
                try{
                    library.extend(memberName, documentTitle);
                    fail("Expected a CannotExtendEx to be thrown for extending a magazine");
                }catch (CannotExtendEx e){
                    assertEquals("Magazine cannot be extended", e.getMessage());
                }
            }
        }catch (InvalidArgumentEx | CannotExtendEx | DuplicateMemberEx | DuplicateDocumentEx | CannotBorrowEx e){
            if(memberName==null || memberName.isEmpty()){
                assertEquals("The document is not in member's loan", e.getMessage());
            }
        }
    }

    @Property
    public void testReturnDocument(String memberName, String documentTitle){
        assumeTrue(memberName!=null && !memberName.isEmpty());
        assumeTrue(documentTitle!=null && !documentTitle.isEmpty());
        Library library = new Library();
        try{
            try{
                library.returnDocument(memberName, documentTitle);
            }catch (InvalidArgumentEx e){
                assertEquals("The document is not in member's loan", e.getMessage());
            }
            library.addProfMember(memberName);
            library.addBook(documentTitle, 2);
            library.borrow(memberName, documentTitle);
            library.timePass(3);
            library.extend(memberName, documentTitle);
            library.timePass(10);
            library.extend(memberName, documentTitle);
            library.timePass(4);
            library.returnDocument(memberName, documentTitle);
            try{
                library.returnDocument(memberName, documentTitle);
            }catch (InvalidArgumentEx e){
                assertEquals("The document is not in member's loan", e.getMessage());
            }
        } catch (InvalidArgumentEx | DuplicateMemberEx | DuplicateDocumentEx | CannotBorrowEx | CannotExtendEx e) {
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
            if(memberName == null || memberName.isEmpty()){
                int dummy =library.getTotalPenalty(memberName);
                fail("Expected InvalidArgumentEx to be thrown");
            }else {
                assumeTrue(title != null && !title.isEmpty());
                assumeTrue(copies > 0);
                library.addProfMember(memberName);
                library.addBook(title, copies);
                library.borrow(memberName, title);
                library.timePass(days);
                assertEquals(expectedPenalty, library.getTotalPenalty(memberName));
            }
        }catch (InvalidArgumentEx | DuplicateMemberEx | DuplicateDocumentEx | CannotBorrowEx e){
            if(memberName == null || memberName.isEmpty()){
                assertEquals("Cannot find member", e.getMessage());
            }
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
            if(memberName == null || memberName.isEmpty()){
                int dummy =library.getTotalPenalty(memberName);
                fail("Expected InvalidArgumentEx to be thrown");
            }
            else {
                assumeTrue(title != null && !title.isEmpty());
                assumeTrue(copies > 0);
                library.addProfMember(memberName);
                library.addReference(title, copies);
                library.borrow(memberName, title);
                library.timePass(days);
                assertEquals(expectedPenalty, library.getTotalPenalty(memberName));
            }
        }catch (InvalidArgumentEx | DuplicateMemberEx | DuplicateDocumentEx | CannotBorrowEx e){
            if(memberName == null || memberName.isEmpty()){
                assertEquals("Cannot find member", e.getMessage());
            }
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
            if(memberName == null || memberName.isEmpty()){
                int dummy =library.getTotalPenalty(memberName);
                fail("Expected InvalidArgumentEx to be thrown");
            }else {
                assumeTrue(title != null && !title.isEmpty());
                assumeTrue(copies > 0);
                assumeTrue(yr > 0);
                assumeTrue(num > 0);
                library.addProfMember(memberName);
                library.addMagazine(title, yr, num, copies);
                library.borrow(memberName, title);
                library.timePass(days);
                assertEquals(expectedPenalty, library.getTotalPenalty(memberName));
            }
        }catch (InvalidArgumentEx | DuplicateMemberEx | DuplicateDocumentEx | CannotBorrowEx e){
            if(memberName == null || memberName.isEmpty()){
                assertEquals("Cannot find member", e.getMessage());
            }
        }
    }
    @Property
    public void testAvailableTitles(Character c,@InRange(min = "0", max="3") int length, String title, int copies){
        assumeTrue(c!=null);
        assumeTrue(!title.isEmpty());
        assumeTrue(copies > 0);
        Library library = new Library();
        List<String> expected = new ArrayList<>();
        try {
            for(int i = 0; i < length; i ++){
                String repeatedC = String.join("", Collections.nCopies(i + 1, Character.toString(c)));
                String temp = title + repeatedC;
                library.addBook(temp, copies + i);
                expected.add(temp);
            }
            List<String> actual = library.availableTitles();
            assertEquals(length, actual.size());
            boolean allSame = true;
            for(int i = 0; i < length; i ++){
                if(!actual.contains(expected.get(i))){
                    allSame = false;
                }
            }
            assumeTrue(allSame);
        }catch (InvalidArgumentEx | DuplicateDocumentEx e){
            fail("Nothing must be thrown :)))) ");
        }
    }
    @Property
    public void testTimePass(int days){
        Library library = new Library();
        try{
            library.timePass(days);
        }catch (InvalidArgumentEx e){
            assertEquals("Cannot go back in time", e.getMessage());
        }
    }
}
