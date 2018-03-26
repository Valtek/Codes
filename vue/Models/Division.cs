using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations.Schema;
using System.Linq;
using System.Threading.Tasks;

namespace Realtors.Models
{
    [Table("tDivision")]
    public class Division
    {
        [Column("id")]
        public int Id { get; set; }

        [Column("name")]
        public string Name { get; set; }

        [Column("creationDate")]
        public DateTime CreationDate { get; set; }
    }
}
