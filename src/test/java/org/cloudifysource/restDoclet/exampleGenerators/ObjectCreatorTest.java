package org.cloudifysource.restDoclet.exampleGenerators;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.SerializationConfig;
import org.junit.Before;
import org.junit.Test;
import static org.hamcrest.CoreMatchers.isA;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.nullValue;

import static com.google.common.collect.Lists.newArrayList;

/**
 * @author edward
 */
public class ObjectCreatorTest {

  private ObjectCreator objectCreator_;

  @Before
  public void setup() {
    objectCreator_ = new ObjectCreator();
  }

  @Test
  public void createsAnObject() throws IllegalAccessException {
    assertThat(objectCreator_.createObject(Fish.class), instanceOf(Fish.class));
  }

  @Test
  public void setsAStringFieldOnAnObject() throws IllegalAccessException {
    Fish fish = (Fish) objectCreator_.createObject(Fish.class);
    assertThat(fish.getName(), notNullValue());
    assertThat(fish.getName().length(), greaterThan(0));
  }

  @Test
  public void setsAPrimitiveFieldOnAnObject() throws IllegalAccessException {
    Fish fish = (Fish) objectCreator_.createObject(Fish.class);
    assertThat(fish.getCount(), instanceOf(long.class));
  }

  @Test
  public void createsNestedClasses() throws IllegalAccessException {
    FishBowl fishBowl = (FishBowl) objectCreator_.createObject(FishBowl.class);
    assertThat(fishBowl.getFish(), instanceOf(Fish.class));
    assertThat(fishBowl.getFish().getName(), notNullValue());
  }

  @Test
  public void createsLists() throws IllegalAccessException, IOException {
    Aquarium aquarium = (Aquarium) objectCreator_.createObject(Aquarium.class);
    assertThat(aquarium.getFishes(), instanceOf(List.class));
    assertThat(aquarium.getFishes(), hasSize(greaterThan(0)));
    assertThat(aquarium.getFishes().get(0), instanceOf(Fish.class));
    assertThat(aquarium.getFishes().get(0).getName(), notNullValue());
  }

  @Test
  public void createsListsOfLists() throws IOException, IllegalAccessException {
    SeaWorld seaWorld = (SeaWorld) objectCreator_.createObject(SeaWorld.class);
    new ObjectMapper()
            .configure(SerializationConfig.Feature.FAIL_ON_EMPTY_BEANS, false)
            .writeValueAsString(seaWorld);
  }

  @Test
  public void createClassWithEnumField() throws IllegalAccessException {
    ClassWithEnumField response = (ClassWithEnumField) objectCreator_.createObject(ClassWithEnumField.class);
    assertThat(response.getStatus(), is(ClassWithEnumField.Status.ACTIVATED));
  }

  @Test
  public void createsEmptyClasses() throws IllegalAccessException {
    EmptyClass empty = (EmptyClass) objectCreator_.createObject(EmptyClass.class);
  }

  @Test
  public void canCreateAnAbstractClass() throws IllegalAccessException {
    ClassWithAnAbstractClassInside classInside =
            (ClassWithAnAbstractClassInside) objectCreator_.createObject(ClassWithAnAbstractClassInside.class);

    assertThat(classInside.getTheAbstractClass(), notNullValue());
  }

  @Test
  public void canCallAbstractMethods() throws IllegalAccessException {
    AbstractClass abstractClass = (AbstractClass) objectCreator_.createObject(AbstractClass.class);
    assertThat(abstractClass.getFoo(), notNullValue());
  }

  @Test
  public void canCreateDateField() throws IllegalAccessException {
    DateClass dateClass = (DateClass) objectCreator_.createObject(DateClass.class);
    assertThat(dateClass.getDate(), not(nullValue()));
  }

  @Test
  public void canCreateAMap() throws IllegalAccessException {
    ClassWithMap mapClass = (ClassWithMap) objectCreator_.createObject(ClassWithMap.class);
    assertThat(mapClass.getMap(), notNullValue());
    Iterator<Map.Entry<Long, String>> it = mapClass.getMap().entrySet().iterator();
    assertThat(it.hasNext(), is(true));
    Map.Entry<Long,String> entry = it.next();
    assertThat(entry.getKey(), isA(Long.class));
    assertThat(entry.getValue(), isA(String.class));
  }

  @Test
  public void canCreateATopLevelList() throws IllegalAccessException {
    List<String> stringList = newArrayList("a");
    Object listObject = objectCreator_.createObject(stringList.getClass());
    assertThat(listObject, instanceOf(List.class));
  }

  static class EmptyClass {
    public static final EmptyClass NOTHING = new EmptyClass();
  }

  static class ClassWithAnAbstractClassInside {
    private AbstractClass abstractClass_;

    public AbstractClass getTheAbstractClass() {
      return abstractClass_;
    }
  }

  static abstract class AbstractClass {
    private String bar_;

    public abstract String getFoo();
  }

  static class Fish {
    private String name_;
    private Long count_;

    public String getName() {
      return name_;
    }

    public long getCount() {
      return count_;
    }
  }

  static class FishBowl {
    private Fish fish_;

    public Fish getFish() {
      return fish_;
    }
  }

  static class Aquarium {
    private List<Fish> fishes_;

    public List<Fish> getFishes() {
      return fishes_;
    }
  }

  static class SeaWorld {
    private List<Aquarium> aquariums_;

    public List<Aquarium> getAquariums()  {
      return aquariums_;
    }
  }

  public static class ClassWithEnumField {
    public enum Status {
      ACTIVATED,
      PENDING
    }

    private Status status_ = Status.ACTIVATED;

    public Status getStatus() {
      return status_;
    }
  }

  public static class DateClass {
    private Date date_;

    public Date getDate() {
      return date_;
    }
  }

  public static class ClassWithMap {
    Map<Long, String> hashMap_ = new HashMap<Long,String>();

    public Map<Long, String> getMap() {
      return hashMap_;
    }
  }
}
